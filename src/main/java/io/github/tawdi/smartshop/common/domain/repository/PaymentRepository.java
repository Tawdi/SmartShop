package io.github.tawdi.smartshop.common.domain.repository;

import io.github.tawdi.smartshop.common.domain.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends LongRepository<Payment> {

    List<Payment> findByOrderId(String orderId);
}
