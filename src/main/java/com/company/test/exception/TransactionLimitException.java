package com.company.test.exception;

public class TransactionLimitException extends RuntimeException {

    public TransactionLimitException(String message) {
        super(message);
    }
}
