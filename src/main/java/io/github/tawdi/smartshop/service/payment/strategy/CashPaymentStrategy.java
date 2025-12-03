package io.github.tawdi.smartshop.service.payment.strategy;

import io.github.tawdi.smartshop.domain.entity.Payment;
import io.github.tawdi.smartshop.enums.PaymentStatus;
import io.github.tawdi.smartshop.exception.BusinessRuleViolationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CashPaymentStrategy implements PaymentStrategy {

    private static final BigDecimal MAX_CASH = new BigDecimal("20000");

    @Override
    public void validate(Payment payment) {
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolationException("Le montant du paiement est requis et doit être positif");
        }
        if (payment.getAmount().compareTo(MAX_CASH) > 0) {
            throw new BusinessRuleViolationException("Paiement en espèces limité à 20 000 DH (Art. 193 CGI)");
        }
        if (payment.getBankName() != null || payment.getDueDate() != null) {
            throw new BusinessRuleViolationException("Pour un paiement en espèces, la banque et la date d'échéance doivent être vides");
        }
    }

    @Override
    public PaymentStatus getDefaultStatus() {
        return PaymentStatus.CASHED;
    }

    @Override
    public void onCreated(Payment payment) {
        payment.setEncaissementDate(LocalDate.now());
    }
}