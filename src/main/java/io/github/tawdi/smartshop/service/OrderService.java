package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.dto.order.OrderRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderResponseDTO;

public interface OrderService extends StringCrudService<Order, OrderRequestDTO, OrderResponseDTO> {

    OrderResponseDTO confirmOrder(String orderId);
}
