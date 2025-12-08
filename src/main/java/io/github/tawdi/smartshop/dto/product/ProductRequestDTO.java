package io.github.tawdi.smartshop.dto.product;

import io.github.tawdi.smartshop.dto.ValidationGroups;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequestDTO {

//    @NotBlank(groups = ValidationGroups.Create.class)
//    private String reference;

    @NotBlank(groups = ValidationGroups.Create.class)
    private String name;

    @Size( max = 500 , groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
    private String description;

    @Min(value = 0,groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
    private BigDecimal priceHT;

    @Min(value = 0,groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
    private Integer stock;
}
