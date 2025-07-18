package com.hoo.universe.adapter.in.web.errorcode;


import com.hoo.universe.application.exception.ErrorCode;

public record ErrorResponse(
        String code,
        String httpStatusReason,
        Integer httpStatusCode,
        String message
) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getStatus().getReasonPhrase(),
                errorCode.getStatus().value(),
                errorCode.getMessage());
    }
}
