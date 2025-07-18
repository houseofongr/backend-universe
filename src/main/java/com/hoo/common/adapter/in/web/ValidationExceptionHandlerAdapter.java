package com.hoo.common.adapter.in.web;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandlerAdapter {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> processValidationError(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .badRequest()
                .body(ValidationErrorResponse.of(exception.getBindingResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationErrorResponse>> processValidationError(ConstraintViolationException exception) {
        return ResponseEntity
                .badRequest()
                .body(ValidationErrorResponse.of(exception.getConstraintViolations()));
    }
}