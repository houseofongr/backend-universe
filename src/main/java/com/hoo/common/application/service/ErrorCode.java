package com.hoo.common.application.service;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();

    String getCode();

    HttpStatus getStatus();

    String getMessage();
}
