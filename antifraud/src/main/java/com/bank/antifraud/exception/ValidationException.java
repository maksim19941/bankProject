package com.bank.antifraud.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final String location;

    public ValidationException(String message, String location) {
        super(message);
        this.location = location;
    }
}
