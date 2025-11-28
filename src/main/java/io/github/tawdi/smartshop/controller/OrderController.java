package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.dto.order.OrderRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderResponseDTO;
import io.github.tawdi.smartshop.mapper.OrderMapper;
import io.github.tawdi.smartshop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Commandes", description = "Commandes management APIs")
public class OrderController extends StringBaseController<Order, OrderRequestDTO, OrderResponseDTO> {

    public OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
    }
}
