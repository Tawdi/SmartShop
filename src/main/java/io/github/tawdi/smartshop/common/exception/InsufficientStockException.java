package io.github.tawdi.smartshop.common.exception;

public class InsufficientStockException extends BusinessRuleViolationException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
