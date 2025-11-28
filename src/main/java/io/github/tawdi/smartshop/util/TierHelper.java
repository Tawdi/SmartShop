package io.github.tawdi.smartshop.util;

import io.github.tawdi.smartshop.enums.CustomerTier;

import java.math.BigDecimal;
import java.util.Map;

public final class TierHelper {

    private TierHelper() {}

    private static final int PLATINUM_ORDER_COUNT = 20;
    private static final int GOLD_ORDER_COUNT = 10;
    private static final int SILVER_ORDER_COUNT = 3;

    private static final BigDecimal PLATINUM_THRESHOLD = new BigDecimal("15000");
    private static final BigDecimal GOLD_THRESHOLD = new BigDecimal("5000");
    private static final BigDecimal SILVER_THRESHOLD = new BigDecimal("1000");

    private static final Map<CustomerTier, BigDecimal> NEXT_TIER_AMOUNTS = Map.of(
            CustomerTier.BASIC, SILVER_THRESHOLD,
            CustomerTier.SILVER,  GOLD_THRESHOLD,
            CustomerTier.GOLD, PLATINUM_THRESHOLD,
            CustomerTier.PLATINUM, BigDecimal.ZERO
    );

    private static final Map<CustomerTier, Double> DISCOUNT_RATES = Map.of(
            CustomerTier.BASIC, 0.0,
            CustomerTier.SILVER, 5.0,
            CustomerTier.GOLD, 10.0,
            CustomerTier.PLATINUM, 15.0
    );



    public static BigDecimal amountForNextTier(CustomerTier tier) {
        return NEXT_TIER_AMOUNTS.get(tier);
    }

    public static double discountRateForTier(CustomerTier tier) {
        return DISCOUNT_RATES.get(tier);
    }

    public static CustomerTier calculateTier(long orderCount, BigDecimal totalSpent) {
        if (orderCount >= PLATINUM_ORDER_COUNT || totalSpent.compareTo(PLATINUM_THRESHOLD) >= 0) {
            return CustomerTier.PLATINUM;
        }
        if (orderCount >= GOLD_ORDER_COUNT || totalSpent.compareTo(GOLD_THRESHOLD) >= 0) {
            return CustomerTier.GOLD;
        }
        if (orderCount >= SILVER_ORDER_COUNT || totalSpent.compareTo(SILVER_THRESHOLD) >= 0) {
            return CustomerTier.SILVER;
        }
        return CustomerTier.BASIC;
    }
}
