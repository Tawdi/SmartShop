package io.github.tawdi.smartshop.service.payment.strategy;

import io.github.tawdi.smartshop.domain.entity.Payment;
import io.github.tawdi.smartshop.enums.PaymentStatus;
import io.github.tawdi.smartshop.exception.BusinessRuleViolationException;
import org.springframework.stereotype.Component;

@Component
public class TransferPaymentStrategy implements PaymentStrategy {

    @Override
    public void validate(Payment payment) {
        if (payment.getReference() == null || payment.getReference().trim().isEmpty()) {
            throw new BusinessRuleViolationException("La référence du virement est obligatoire");
        }
        if (payment.getBankName() == null || payment.getBankName().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Le nom de la banque est obligatoire pour un virement");
        }
    }

    @Override
    public PaymentStatus getDefaultStatus() {
        return PaymentStatus.PENDING;
    }

    @Override
    public void onCreated(Payment payment) {

    }
}
