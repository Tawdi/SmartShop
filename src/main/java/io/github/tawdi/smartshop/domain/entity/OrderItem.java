package io.github.tawdi.smartshop.domain.entity;

import io.github.tawdi.smartshop.domain.entity.id.LongEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends LongEntity {

    @Column(nullable = false)
    @Min(1)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal unitPriceHT;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal lineTotalHT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}