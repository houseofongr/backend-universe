package com.hoo.universe.application.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();

    String getCode();

    HttpStatus getStatus();

    String getMessage();
}
