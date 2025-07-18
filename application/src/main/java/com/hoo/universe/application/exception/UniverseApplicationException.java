package com.hoo.universe.application.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UniverseApplicationException extends RuntimeException {

    private final ApplicationErrorCode error;
    private final String message;

    public UniverseApplicationException(ApplicationErrorCode error) {
        log.error("Application Error : {}", error.getMessage());
        this.error = error;
        this.message = error.getMessage();
    }

    public UniverseApplicationException(ApplicationErrorCode error, String message) {
        log.error("Application Error : {}", message);
        this.error = error;
        this.message = message;
    }

}
