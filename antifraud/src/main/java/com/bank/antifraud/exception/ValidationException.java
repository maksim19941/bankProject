package com.bank.antifraud.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends RuntimeException {

    private final String location;
    private final HttpStatus httpStatus;
    public ValidationException(String message, String location, HttpStatus httpStatus) {
        super(message);
        this.location = location;
        this.httpStatus = httpStatus;
    }
}
