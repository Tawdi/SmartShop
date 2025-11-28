package io.github.tawdi.smartshop.dto.order;

import io.github.tawdi.smartshop.dto.BaseResponseDTO;
import io.github.tawdi.smartshop.dto.client.ClientResponseDTO;
import io.github.tawdi.smartshop.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class OrderResponseDTO extends BaseResponseDTO<String> {

    private ClientResponseDTO client;
    private LocalDateTime orderDate;

    private BigDecimal subtotalHT;
    private BigDecimal discountAmount;
    private String appliedDiscountCode;
    private BigDecimal amountAfterDiscountHT;
    private BigDecimal tvaAmount;
    private BigDecimal totalTTC;

    private BigDecimal remainingAmount;
    private OrderStatus status;
    private LocalDateTime confirmedAt;
    private String confirmedBy;

    private List<OrderItemResponseDTO> orderItems;
}
