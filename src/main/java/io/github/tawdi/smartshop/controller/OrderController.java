package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.auth.annotation.RequireRole;
import io.github.tawdi.smartshop.auth.annotation.Role;
import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.dto.ApiResponseDTO;
import io.github.tawdi.smartshop.dto.ValidationGroups;
import io.github.tawdi.smartshop.dto.order.OrderRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderResponseDTO;
import io.github.tawdi.smartshop.dto.order.OrderSearchCriteria;
import io.github.tawdi.smartshop.enums.OrderStatus;
import io.github.tawdi.smartshop.mapper.OrderMapper;
import io.github.tawdi.smartshop.service.CurrentUserService;
import io.github.tawdi.smartshop.service.OrderService;
import io.github.tawdi.smartshop.specification.OrderSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Commandes", description = "Commandes management APIs")
public class OrderController extends StringBaseController<Order, OrderRequestDTO, OrderResponseDTO> {

    private final OrderService orderService;
    private final CurrentUserService currentUserService;


    public OrderController(OrderService service, OrderMapper mapper,CurrentUserService currentUserService) {
        super(service, mapper);
        this.orderService = service;
        this.currentUserService = currentUserService;

    }

    @Override
    @PostMapping
    @RequireRole(Role.ADMIN)
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

    @GetMapping("/filters")
    @RequireRole(Role.ADMIN)
    public ResponseEntity<ApiResponseDTO<Page<OrderResponseDTO>>> getAllWithFilters(OrderSearchCriteria criteria, @ParameterObject Pageable pageable){

        Specification<Order> spec = OrderSpecification.withCriteria(criteria, null);
        Page<OrderResponseDTO> page = orderService.findAll(pageable,spec);
        return ResponseEntity.ok(ApiResponseDTO.success("Listes des commandes ", page));
    }


    @PostMapping("/{id}/comfirm")
    @RequireRole(Role.ADMIN)
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> confirmOrder(@PathVariable String id) {

        OrderResponseDTO response = orderService.confirmOrder(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Commande confirmée avec succès", response)
        );
    }

    @PatchMapping("/{id}/cancel")
    @RequireRole(Role.ADMIN)
    @Operation(summary = "Annuler une commande (ADMIN uniquement)")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> cancelOrder(@PathVariable String id) {
        OrderResponseDTO canceled = orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Commande annulée avec succès et stock re-crédité", canceled
        ));
    }

    @GetMapping("/my-orders")
    @RequireRole(Role.CLIENT)
    @Operation(summary = "Historique des commandes du client connecté")
    public ResponseEntity<ApiResponseDTO<Page<OrderResponseDTO>>> getOrdersHistoryForClient(OrderSearchCriteria criteria, @ParameterObject Pageable pageable, HttpServletRequest request) {

        String clientId = currentUserService.getCurrentClientId(request);
        Specification<Order> spec = OrderSpecification.withCriteria(criteria, clientId);
        Page<OrderResponseDTO> page = orderService.findAll(pageable,spec);

        return ResponseEntity.ok(ApiResponseDTO.success("Historique des commandes filtrées", page));
    }

}
