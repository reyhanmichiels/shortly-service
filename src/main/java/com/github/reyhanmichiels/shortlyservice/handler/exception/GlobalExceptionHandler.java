package com.github.reyhanmichiels.shortlyservice.handler.exception;

import com.github.reyhanmichiels.shortlyservice.business.dto.http.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<HttpResponse<Void>> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpResponse.error(request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}
