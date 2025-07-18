package com.hoo.file.application.service;

import com.hoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum FileErrorCode implements ErrorCode {

    FILE_SIZE_LIMIT_EXCEED("FILE-COMMON-1", BAD_REQUEST, "파일 용량 한도를 초과했습니다."),
    INVALID_FILE_EXTENSION("FILE-COMMON-1", BAD_REQUEST, "유효하지 않은 파일 확장자입니다."),
    FILE_NAME_EMPTY("FILE-COMMON-3", BAD_REQUEST, "파일 이름이 존재하지 않습니다."),
    INVALID_FILE_TYPE("FILE-COMMON-4", BAD_REQUEST, "잘못된 타입의 파일 요청입니다."),
    FILE_NOT_FOUND("FILE-COMMON-5", NOT_FOUND, "파일을 찾을 수 없습니다."),
    FILE_NAME_DUPLICATION("FILE-COMMON-6", CONFLICT, "중복된 파일명입니다."),
    RETRIEVE_FILE_FAILED("FILE-COMMON-7", INTERNAL_SERVER_ERROR, "파일을 불러오는 데 실패했습니다."),
    NEW_FILE_CREATION_FAILED("FILE-COMMON-8", INTERNAL_SERVER_ERROR, "새로운 파일을 생성하는 데 실패했습니다."),
    DELETE_FILE_FAILED("FILE-COMMON-9", INTERNAL_SERVER_ERROR, "파일을 삭제하는데 실패했습니다."),

    INVALID_AUTHORITY("FILE-PRIVATE-1", FORBIDDEN, "잘못된 권한의 파일 요청입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
