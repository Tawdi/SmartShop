package io.github.tawdi.smartshop.dto.order;

import java.math.BigDecimal;
import java.time.Instant;

public record PromoCodeResponseDTO(
        String code,
        BigDecimal discountRate,
        boolean used,
        Instant createdAt,
        Instant updatedAt) {
}