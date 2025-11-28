package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends StringRepository<Order> {

    Page<Order> findByClientId(String clientId, Pageable pageable);

}
