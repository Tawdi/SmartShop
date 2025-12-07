package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends LongRepository<OrderItem> {

    Boolean existsByProductId(String productId);
}
