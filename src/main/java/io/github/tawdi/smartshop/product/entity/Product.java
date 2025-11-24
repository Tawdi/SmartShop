package io.github.tawdi.smartshop.product.entity;

import io.github.tawdi.smartshop.common.domain.entity.id.StringEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SoftDelete
public class Product extends StringEntity {

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank
    private String reference;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    @Min(0)
    private BigDecimal priceHT;

    @Column(nullable = false)
    @Min(0)
    private Integer stock;

}