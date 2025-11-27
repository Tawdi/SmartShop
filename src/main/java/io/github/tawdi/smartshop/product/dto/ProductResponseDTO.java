package io.github.tawdi.smartshop.product.dto;

import io.github.tawdi.smartshop.common.api.dto.BaseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
public class ProductResponseDTO extends BaseResponseDTO<String> {

    private String reference;

    private String name;

    private String description;

    private BigDecimal priceHT;

    private Integer stock;
}
