package io.github.tawdi.smartshop.exception;

public class InsufficientStockException extends BusinessRuleViolationException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
