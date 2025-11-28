package io.github.tawdi.smartshop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private String productId;
    private String productName;
    private BigDecimal unitPriceHT;
    private int quantity;
    private BigDecimal lineTotalHT;
}