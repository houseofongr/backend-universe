package com.hoo.aar.adapter.in.web;

import com.hoo.aar.application.service.AarException;
import com.hoo.common.adapter.in.web.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AarExceptionHandlerAdapter {

    @ExceptionHandler(AarException.class)
    public ResponseEntity<?> handler(AarException e) {
        log.error("AAR Exception {} has been occurred {}", e.getError().getCode(), e.getMessage());
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ErrorResponse.of(e.getError()));
    }
}