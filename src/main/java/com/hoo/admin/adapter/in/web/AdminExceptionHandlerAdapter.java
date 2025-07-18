package com.hoo.admin.adapter.in.web;

import com.hoo.admin.application.service.AdminException;
import com.hoo.common.adapter.in.web.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AdminExceptionHandlerAdapter {

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<?> handler(AdminException e) {
        log.error("Admin Exception {} has been occurred {}", e.getError().getCode(), e.getMessage());
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ErrorResponse.of(e.getError()));
    }
}
