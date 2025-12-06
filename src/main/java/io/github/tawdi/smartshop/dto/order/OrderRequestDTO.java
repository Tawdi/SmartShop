package io.github.tawdi.smartshop.dto.order;

import io.github.tawdi.smartshop.dto.ValidationGroups;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull(groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class},message = "L'ID du client est obligatoire")
    private String clientId;

    @Pattern(groups = {ValidationGroups.Create.class },
            regexp = "^$|PROMO-[A-Z0-9]{4}",
            message = "Le code promo doit respecter le format PROMO-XXXX")
    private String discountCode;

    @NotEmpty(groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class}, message = "La commande doit contenir au moins un article")
    @Valid
    private List<OrderItemRequestDTO> items;
}
