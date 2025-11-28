package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends LongRepository<Payment> {

    List<Payment> findByOrderId(String orderId);
}
