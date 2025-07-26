package com.hoo.universe.adapter.in.web.errorcode;

import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.application.exception.UniverseApplicationException;
import com.hoo.universe.application.exception.UniverseDomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UniverseExceptionHandler {

    @ExceptionHandler(UniverseDomainException.class)
    public ResponseEntity<ErrorResponse> handle(UniverseDomainException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(ErrorResponse.of(exception.getError()));
    }

    @ExceptionHandler(UniverseApplicationException.class)
    public ResponseEntity<ErrorResponse> handle(UniverseApplicationException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(ErrorResponse.of(exception.getError()));
    }

    @ExceptionHandler(UniverseAdapterException.class)
    public ResponseEntity<ErrorResponse> handle(UniverseAdapterException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(ErrorResponse.of(exception.getError()));
    }
}
