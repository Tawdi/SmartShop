package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.domain.entity.Payment;
import io.github.tawdi.smartshop.domain.repository.OrderRepository;
import io.github.tawdi.smartshop.domain.repository.PaymentRepository;
import io.github.tawdi.smartshop.dto.payment.CreatePaymentRequestDTO;
import io.github.tawdi.smartshop.dto.payment.PaymentResponseDTO;
import io.github.tawdi.smartshop.dto.payment.UpdatePaymentStatusRequestDTO;
import io.github.tawdi.smartshop.enums.OrderStatus;
import io.github.tawdi.smartshop.enums.PaymentStatus;
import io.github.tawdi.smartshop.enums.PaymentType;
import io.github.tawdi.smartshop.exception.BusinessRuleViolationException;
import io.github.tawdi.smartshop.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.service.PaymentService;
import io.github.tawdi.smartshop.service.payment.strategy.CashPaymentStrategy;
import io.github.tawdi.smartshop.service.payment.strategy.ChequePaymentStrategy;
import io.github.tawdi.smartshop.service.payment.strategy.PaymentStrategy;
import io.github.tawdi.smartshop.service.payment.strategy.TransferPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final Map<PaymentType, PaymentStrategy> strategies = Map.of
            (
                    PaymentType.CASH, new CashPaymentStrategy(),
                    PaymentType.CHECK, new ChequePaymentStrategy(),
                    PaymentType.BANK_TRANSFER, new TransferPaymentStrategy()
            );


    @Override
    @Transactional
    public PaymentResponseDTO create(CreatePaymentRequestDTO dto, String orderId) {

        Order order = loadOrder(orderId);

        validateOrderStatus(order);
        validateAmount(dto, order);


        PaymentStrategy strategy = strategies.get(dto.getType());
        if (strategy == null) {
            throw new BusinessRuleViolationException("Moyen de paiement non supporté : " + dto.getType());
        }

        Payment payment = buildPayment(dto, order);


        strategy.validate(payment);
        payment.setStatus(strategy.getDefaultStatus());
        strategy.onCreated(payment);

        Payment saved = paymentRepository.save(payment);


        order.setRemainingAmount(order.getRemainingAmount().subtract(dto.getAmount()));


        recalculerMontantRestant(order);
        orderRepository.save(order);

        return toDto(saved);
    }

    @Override
    @Transactional
    public PaymentResponseDTO updateStatus(Long paymentId, UpdatePaymentStatusRequestDTO dto) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé : " + paymentId));

        Order order = payment.getOrder();

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessRuleViolationException(
                    "Impossible de modifier un paiement d'une commande déjà " + order.getStatus());
        }

        PaymentStatus oldStatus = payment.getStatus();
        PaymentStatus newStatus = dto.getStatus();

        payment.setStatus(newStatus);

        if (newStatus == PaymentStatus.CASHED) {
            payment.setEncaissementDate(LocalDate.now());
        } else {
            payment.setEncaissementDate(null);
        }

        Payment saved = paymentRepository.save(payment);

        recalculerMontantRestant(order);
        orderRepository.save(order);

        return toDto(saved);
    }

    private Order loadOrder(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));
    }

    private void validateOrderStatus(Order order) {
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessRuleViolationException(
                    "Impossible d'ajouter un paiement à une commande non PENDING"
            );
        }

        if (order.getRemainingAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolationException(
                    "Cette commande est déjà entièrement payée."
            );
        }
    }


    private void validateAmount(CreatePaymentRequestDTO dto, Order order) {
        if (dto.getAmount() == null || dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolationException(
                    "Le montant du paiement doit être strictement positif."
            );
        }

        if (dto.getAmount().compareTo(order.getRemainingAmount()) > 0) {
            throw new BusinessRuleViolationException(
                    "Le montant du paiement dépasse le montant restant dû."
            );
        }
    }

    private Payment buildPayment(CreatePaymentRequestDTO dto, Order order) {
        Payment payment = new Payment();
        payment.setPaymentNumber(order.getPayments().size() + 1);

        String autoGeneratedRef = generatePaymentReference(dto.getType());

        payment.setAmount(dto.getAmount());
        payment.setType(dto.getType());
//        payment.setReference(dto.getReference());
        payment.setReference(dto.getReference() != null && !dto.getReference().isBlank()
                ? dto.getReference().trim().toUpperCase()
                : autoGeneratedRef);
        payment.setBankName(dto.getBankName());
        payment.setDueDate(dto.getDueDate());
        payment.setPaymentDate(LocalDate.now());
        payment.setOrder(order);
        return payment;
    }

    public void recalculerMontantRestant(Order order) {
        BigDecimal totalEncaisse = paymentRepository.findByOrderId(order.getId())
                .stream()
                .filter(p -> p.getStatus() == PaymentStatus.CASHED)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remaining = order.getTotalTTC().subtract(totalEncaisse);

        order.setRemainingAmount(
                remaining.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : remaining
        );
    }

    public PaymentResponseDTO toDto(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setReference(payment.getReference());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setType(payment.getType());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setOrderId(payment.getOrder().getId());
        return dto;
    }

    private String generatePaymentReference(PaymentType type) {
        return switch (type) {
            case CASH -> generateCashReference();
            case CHECK -> generateChequeReference();
            case BANK_TRANSFER -> generateTransferReference();
        };
    }

    private String generateCashReference() {
        String prefix = "REÇU-";
        long count = paymentRepository.countByType(PaymentType.CASH) + 1;
        return prefix + String.format("%03d", count);
    }

    private String generateChequeReference() {
        String reference;
        do {
            int number = ThreadLocalRandom.current().nextInt(1_000_000, 10_000_000);
            reference = "CHQ-" + number;
        } while (paymentRepository.existsByReference(reference));
        return reference;
    }

    private String generateTransferReference() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String prefix = "VIR-" + today + "-";

        String reference;
        int suffix;
        do {
            suffix = ThreadLocalRandom.current().nextInt(1000, 9999);
            reference = prefix + suffix;
        } while (paymentRepository.existsByReference(reference));

        return reference;
    }


}
