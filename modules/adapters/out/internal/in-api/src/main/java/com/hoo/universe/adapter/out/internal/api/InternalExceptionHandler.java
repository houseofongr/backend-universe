package com.hoo.universe.adapter.out.internal.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class InternalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handle(WebClientResponseException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(ErrorResponse.builder(exception, exception.getStatusCode(), exception.getResponseBodyAsString()).build());
    }
}
