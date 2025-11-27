package io.github.tawdi.smartshop.common.domain.repository;

import io.github.tawdi.smartshop.common.domain.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends LongRepository<OrderItem> {
}
