package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.dto.ApiResponseDTO;
import io.github.tawdi.smartshop.dto.ValidationGroups;
import io.github.tawdi.smartshop.dto.order.OrderRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderResponseDTO;
import io.github.tawdi.smartshop.enums.OrderStatus;
import io.github.tawdi.smartshop.mapper.OrderMapper;
import io.github.tawdi.smartshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Commandes", description = "Commandes management APIs")
public class OrderController extends StringBaseController<Order, OrderRequestDTO, OrderResponseDTO> {

    private final OrderService orderService;

    public OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
        this.orderService = service;
    }

    @Override
    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> create(
            @Validated(ValidationGroups.Create.class) @RequestBody OrderRequestDTO requestDTO) {

        OrderResponseDTO responseDTO = orderService.save(requestDTO);

        if (OrderStatus.REJECTED.equals(responseDTO.getStatus())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponseDTO.success(
                            "Commande rejetée : stock insuffisant pour un ou plusieurs produits",
                            responseDTO
                    ));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Commande créée avec succès", responseDTO));
    }


    @PostMapping("/{id}/comfirm")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> confirmOrder(@PathVariable String id) {

        OrderResponseDTO response = orderService.confirmOrder(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Commande confirmée avec succès", response)
        );
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Annuler une commande (ADMIN uniquement)")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> cancelOrder(@PathVariable String id) {
        OrderResponseDTO canceled = orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Commande annulée avec succès et stock re-crédité", canceled
        ));
    }

}
