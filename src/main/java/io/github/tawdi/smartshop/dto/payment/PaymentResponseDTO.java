package io.github.tawdi.smartshop.dto.payment;

import io.github.tawdi.smartshop.enums.PaymentStatus;
import io.github.tawdi.smartshop.enums.PaymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentResponseDTO {
    private Long id;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentType type;
    private LocalDate paymentDate;

    private String orderId;
}