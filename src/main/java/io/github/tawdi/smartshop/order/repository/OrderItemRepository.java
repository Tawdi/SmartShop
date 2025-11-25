package io.github.tawdi.smartshop.order.repository;

import io.github.tawdi.smartshop.common.domain.repository.LongRepository;
import io.github.tawdi.smartshop.order.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends LongRepository<OrderItem> {
}
