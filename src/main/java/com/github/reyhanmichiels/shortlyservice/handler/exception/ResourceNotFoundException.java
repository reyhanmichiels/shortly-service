package com.github.reyhanmichiels.shortlyservice.handler.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
