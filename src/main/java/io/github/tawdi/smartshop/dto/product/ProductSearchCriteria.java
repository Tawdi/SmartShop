package io.github.tawdi.smartshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCriteria {

    private String reference;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minStock;
    private Integer maxStock;
    private Boolean availableOnly;   // stock > 0
    private Boolean includeDeleted;
}