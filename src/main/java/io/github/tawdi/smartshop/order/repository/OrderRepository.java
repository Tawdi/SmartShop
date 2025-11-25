package io.github.tawdi.smartshop.order.repository;

import io.github.tawdi.smartshop.common.domain.repository.StringRepository;
import io.github.tawdi.smartshop.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends StringRepository<Order> {

    Page<Order> findByClientId(String clientId, Pageable pageable);

}
