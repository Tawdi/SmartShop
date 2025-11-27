package io.github.tawdi.smartshop.common.domain.entity;

import io.github.tawdi.smartshop.common.domain.entity.id.StringEntity;
import io.github.tawdi.smartshop.common.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends StringEntity {

    @NotNull
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotalHT;

    @Column(precision = 12, scale = 2)
    private BigDecimal discountAmount;

    @Column(length = 20)
    private String discountCode;  // PROMO-XXXX

    @Column(precision = 12, scale = 2)
    private BigDecimal tvaAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalTTC;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal remainingAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    private LocalDateTime confirmedAt;

    @Column(length = 50)
    private String confirmedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;
}