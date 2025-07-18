package com.hoo.common.adapter.in.web;

import com.hoo.common.application.service.ErrorCode;

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
