package io.github.tawdi.smartshop.order.repository;

import io.github.tawdi.smartshop.common.domain.repository.LongRepository;
import io.github.tawdi.smartshop.order.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends LongRepository<Payment> {

    List<Payment> findByOrderId(String orderId);
}
