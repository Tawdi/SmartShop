package io.github.tawdi.smartshop.service.payment.strategy;

import io.github.tawdi.smartshop.domain.entity.Payment;
import io.github.tawdi.smartshop.enums.PaymentStatus;

public interface PaymentStrategy {

    void validate(Payment payment);

    PaymentStatus getDefaultStatus();

    void onCreated(Payment payment);
}
