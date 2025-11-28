package io.github.tawdi.smartshop.dto.product;

import io.github.tawdi.smartshop.dto.BaseResponseDTO;
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
