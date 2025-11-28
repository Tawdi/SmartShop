package io.github.tawdi.smartshop.util;

import io.github.tawdi.smartshop.enums.CustomerTier;

import java.util.Map;

public class TierHelper {

    private record TierInfo(String amountForNextTier, double discountRate) {
    }

    public static final Map<CustomerTier, TierInfo> tiers =
            Map.of(
                    CustomerTier.BASIC, new TierInfo("1000", 0.0),
                    CustomerTier.SILVER, new TierInfo("5000", 5.0),
                    CustomerTier.GOLD, new TierInfo("15000", 10.0),
                    CustomerTier.PLATINUM, new TierInfo("0", 15.0)
            );


    public static String amountForNextTier(CustomerTier tier) {
        return tiers.get(tier).amountForNextTier;
    }

    public static double discountRateForTier(CustomerTier tier) {
        return tiers.get(tier).discountRate;
    }
}
