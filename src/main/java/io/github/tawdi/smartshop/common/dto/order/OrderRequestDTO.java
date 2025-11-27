package io.github.tawdi.smartshop.common.dto.order;

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

    @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;

    @Pattern(regexp = "^$|PROMO-[A-Z0-9]{4}",
            message = "Le code promo doit respecter le format PROMO-XXXX")
    private String discountCode;

    @NotEmpty(message = "La commande doit contenir au moins un article")
    @Valid
    private List<OrderItemRequestDTO> items;
}
