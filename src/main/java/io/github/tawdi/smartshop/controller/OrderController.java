package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.dto.ApiResponseDTO;
import io.github.tawdi.smartshop.dto.order.OrderRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderResponseDTO;
import io.github.tawdi.smartshop.mapper.OrderMapper;
import io.github.tawdi.smartshop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Commandes", description = "Commandes management APIs")
public class OrderController extends StringBaseController<Order, OrderRequestDTO, OrderResponseDTO> {

    private final OrderService orderService;

    public OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
        this.orderService = service;
    }


    @PostMapping("/{id}/comfirm")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> confirmOrder(@PathVariable String id) {

        OrderResponseDTO response = orderService.confirmOrder(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Commande confirmée avec succès", response)
        );
    }
}
