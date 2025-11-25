package io.github.tawdi.smartshop.order.repository;

import io.github.tawdi.smartshop.common.domain.repository.LongRepository;
import io.github.tawdi.smartshop.order.entity.Payment;

import java.util.List;

public interface PaymentRepository extends LongRepository<Payment> {

    List<Payment> findByOrderId(Long orderId);
}
