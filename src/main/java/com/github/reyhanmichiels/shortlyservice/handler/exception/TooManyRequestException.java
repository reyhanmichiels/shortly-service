package com.github.reyhanmichiels.shortlyservice.handler.exception;

public class TooManyRequestException extends RuntimeException {
    public TooManyRequestException(String message) {
        super(message);
    }
}
