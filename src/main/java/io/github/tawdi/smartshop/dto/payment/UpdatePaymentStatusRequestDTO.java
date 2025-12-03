package io.github.tawdi.smartshop.dto.payment;

import io.github.tawdi.smartshop.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequestDTO {

    @NotNull(message = "Le nouveau statut est obligatoire")
    private PaymentStatus status;
}