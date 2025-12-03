package io.github.tawdi.smartshop.dto.payment;

import io.github.tawdi.smartshop.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreatePaymentRequestDTO {

    @NotNull(message = "Le type de paiement est obligatoire")
    private PaymentType type;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private BigDecimal amount;

    @NotBlank(message = "La référence est obligatoire")
    @Size(max = 50)
    private String reference;

    @Size(max = 100)
    private String bankName;

    private LocalDate dueDate;

}