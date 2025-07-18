package com.hoo.common.application.service;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final ErrorCode error;
    private final String message;

    public ApplicationException(ErrorCode error) {
        this.error = error;
        this.message = error.getMessage();
    }

    public ApplicationException(ErrorCode error, String message) {
        this.error = error;
        this.message = message;
    }

}
