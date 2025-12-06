package io.github.tawdi.smartshop.dto.order;

import io.github.tawdi.smartshop.dto.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PromoCodeRequestDTO(
        @NotBlank(groups = {ValidationGroups.Create.class},message = "Le code est obligatoire")
        @Pattern(groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class}, regexp = "PROMO-[A-Z0-9]{4}", message = "Format attendu : PROMO-XXXX")
        String code,

        @Positive(groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
        BigDecimal discountRate) {
}