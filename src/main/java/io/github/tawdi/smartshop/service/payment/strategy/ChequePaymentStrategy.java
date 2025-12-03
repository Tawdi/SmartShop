package io.github.tawdi.smartshop.service.payment.strategy;

import io.github.tawdi.smartshop.domain.entity.Payment;
import io.github.tawdi.smartshop.enums.PaymentStatus;
import io.github.tawdi.smartshop.exception.BusinessRuleViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Component
public class ChequePaymentStrategy implements PaymentStrategy {

    @Override
    public void validate(Payment payment) {
        if (payment.getReference() == null || payment.getReference().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Le numéro de chèque est obligatoire");
        }
        if (payment.getBankName() == null || payment.getBankName().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Le nom de la banque est obligatoire pour un chèque");
        }
        if (payment.getDueDate() == null) {
            throw new BusinessRuleViolationException("La date d'échéance est obligatoire pour un chèque");
        }
        if (payment.getDueDate().isBefore(LocalDate.now())) {
            throw new BusinessRuleViolationException("La date d'échéance du chèque ne peut pas être antérieure à aujourd'hui");
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
