package com.example.tp5integrado.application;

/**
 * Exceção de negócio para fail early / fail gracefully.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
