package com.hoo.universe.application.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UniverseAdapterException extends RuntimeException {
    private final AdapterErrorCode error;
    private final String message;

    public UniverseAdapterException(AdapterErrorCode error) {
        log.error("Application Error : {}", error.getMessage());
        this.error = error;
        this.message = error.getMessage();
    }

    public UniverseAdapterException(AdapterErrorCode error, String message) {
        log.error("Application Error : {}", message);
        this.error = error;
        this.message = message;
    }

}
