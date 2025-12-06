package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.Client;
import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.domain.entity.OrderItem;
import io.github.tawdi.smartshop.domain.entity.Product;
import io.github.tawdi.smartshop.domain.repository.ClientRepository;
import io.github.tawdi.smartshop.domain.repository.OrderRepository;
import io.github.tawdi.smartshop.domain.repository.ProductRepository;
import io.github.tawdi.smartshop.dto.client.ClientStats;
import io.github.tawdi.smartshop.dto.order.OrderItemRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderResponseDTO;
import io.github.tawdi.smartshop.enums.CustomerTier;
import io.github.tawdi.smartshop.enums.OrderStatus;
import io.github.tawdi.smartshop.exception.BusinessRuleViolationException;
import io.github.tawdi.smartshop.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.mapper.OrderMapper;
import io.github.tawdi.smartshop.service.OrderService;
import io.github.tawdi.smartshop.util.TierHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl extends StringCrudServiceImpl<Order, OrderRequestDTO, OrderResponseDTO> implements OrderService {

    @Value("${app.tva.rate:0.20}")
    private BigDecimal tvaRate;

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper, ClientRepository clientRepository, ProductRepository productRepository) {
        super(repository, mapper);
        this.orderMapper = mapper;
        this.orderRepository = repository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO save(OrderRequestDTO dto) {

        Client client = loadClient(dto.getClientId());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotalHT = BigDecimal.ZERO;
        boolean hasInsufficientStock = false;
        // Charger les produits
        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            Product product = loadProduct(itemDTO.getProductId(), false);

            if (product.getStock() < itemDTO.getQuantity()) {
                hasInsufficientStock = true;
//                throw new BusinessRuleViolationException(
//                        String.format("Stock insuffisant pour le produit %s. Disponible : %d, Demandé : %d",
//                                product.getName(), product.getStock(), itemDTO.getQuantity())
//                );
            }

            BigDecimal line = product.getPriceHT().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            subtotalHT = subtotalHT.add(line);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPriceHT(product.getPriceHT())
                    .lineTotalHT(line)
                    .build();

            orderItems.add(orderItem);
        }

        // Calculer la remise de Tier
        CustomerTier tier = client.getTier();
        BigDecimal loyaltyDiscount = BigDecimal.ZERO;

        if (tier == CustomerTier.SILVER && subtotalHT.compareTo(new BigDecimal("500")) >= 0) {
            loyaltyDiscount = subtotalHT.multiply(new BigDecimal("0.05"));
        } else if (tier == CustomerTier.GOLD && subtotalHT.compareTo(new BigDecimal("800")) >= 0) {
            loyaltyDiscount = subtotalHT.multiply(new BigDecimal("0.10"));
        } else if (tier == CustomerTier.PLATINUM && subtotalHT.compareTo(new BigDecimal("1200")) >= 0) {
            loyaltyDiscount = subtotalHT.multiply(new BigDecimal("0.15"));
        }

        // Appliquer le code promo

        BigDecimal promoDiscount = BigDecimal.ZERO;
        String appliedPromoCode = null;

        // Calcul total des remises
        BigDecimal totalDiscount = loyaltyDiscount.add(promoDiscount);

        // Montant HT après remise
        BigDecimal amountAfterDiscount = subtotalHT.subtract(totalDiscount);

        // TVA 20% sur le montant APRÈS remise (règle marocaine)

        BigDecimal tvaAmount = amountAfterDiscount.multiply(tvaRate)
                .setScale(2, RoundingMode.HALF_UP);

        // Total TTC
        BigDecimal totalTTC = amountAfterDiscount.add(tvaAmount)
                .setScale(2, RoundingMode.HALF_UP);

        Order order = new Order();
        order.setClient(client);
        order.setOrderDate(LocalDateTime.now());
        order.setSubtotalHT(subtotalHT.setScale(2, RoundingMode.HALF_UP));
        order.setDiscountAmount(totalDiscount.setScale(2, RoundingMode.HALF_UP));
        order.setDiscountCode(appliedPromoCode);
        order.setTvaAmount(tvaAmount);
        order.setTotalTTC(totalTTC);
        order.setRemainingAmount(hasInsufficientStock ? BigDecimal.ZERO : totalTTC);
        order.setStatus(hasInsufficientStock ? OrderStatus.REJECTED : OrderStatus.PENDING);


        for (OrderItem item : orderItems) {
            item.setOrder(order);
//            order.getOrderItems().add(item);
        }
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        if (!hasInsufficientStock) {
            for (OrderItem item : savedOrder.getOrderItems()) {
                Product p = item.getProduct();
                p.setStock(p.getStock() - item.getQuantity());
                productRepository.save(p);
            }
        }
        return orderMapper.toDto(savedOrder);
    }


    @Transactional
    public OrderResponseDTO cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        if (!OrderStatus.PENDING.equals(order.getStatus())) {
            throw new BusinessRuleViolationException(
                    "Seule une commande en statut PENDING peut être annulée. Statut actuel : " + order.getStatus()
            );
        }

        // Remettre le stock
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELED);
        Order saved = orderRepository.save(order);

        return orderMapper.toDto(saved);
    }

    @Transactional
    public OrderResponseDTO confirmOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        if (!OrderStatus.PENDING.equals(order.getStatus())) {
            throw new BusinessRuleViolationException("Seules les commandes PENDING peuvent être confirmées");
        }

        if (order.getRemainingAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessRuleViolationException("La commande doit être totalement payée avant confirmation");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order = orderRepository.save(order);

        Client client = order.getClient();

        ClientStats stats = clientRepository.getClientStatistics(client.getId());
        long confirmedOrders = stats != null ? stats.confirmedOrdersCount() : 0L;
        BigDecimal totalConfirmedAmount = stats != null ? stats.totalConfirmedAmount() : BigDecimal.ZERO;

        client = updateClientTier(client, confirmedOrders, totalConfirmedAmount);

        return orderMapper.toDto(order);
    }


    private Client updateClientTier(Client client, long confirmedOrderCount, BigDecimal totalConfirmedAmount) {

        CustomerTier newTier = TierHelper.calculateTier(confirmedOrderCount, totalConfirmedAmount);

        if (client.getTier() == null || newTier.ordinal() > client.getTier().ordinal()) {
            client.setTier(newTier);
            client = clientRepository.save(client);
        }

        return client;
    }

    private Client loadClient(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID : " + id));
    }

    private Product loadProduct(String id, Boolean isDeleted) {
        return productRepository.findByIdAndDeleted(id, isDeleted)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'ID : " + id));
    }
}
