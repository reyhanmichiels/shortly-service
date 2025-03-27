package com.github.reyhanmichiels.shortlyservice.handler.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
