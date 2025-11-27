package io.github.tawdi.smartshop.common.dto.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClientWithStatisticsDTO extends ClientResponseDTO {

    private Long totalOrders;
    private Long confirmedOrders;
    private BigDecimal totalConfirmedAmount;
    private BigDecimal totalSpent;

    private Double currentDiscountRate;
    private String nextTier;
    private BigDecimal amountNeededForNextTier;

}
