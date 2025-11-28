package io.github.tawdi.smartshop.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull(message = "L'ID du produit est obligatoire")
    private String productId;

    @Min(value = 1, message = "La quantité doit être au minimum 1")
    private int quantity;
}