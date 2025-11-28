package io.github.tawdi.smartshop.dto.client;

import java.math.BigDecimal;

public record ClientStats(
        Long totalOrders,
        Long confirmedOrdersCount,
        BigDecimal totalConfirmedAmount,
        BigDecimal totalPaidAmount
) {
}
