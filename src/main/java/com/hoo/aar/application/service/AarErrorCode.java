package com.hoo.aar.application.service;

import com.hoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AarErrorCode implements ErrorCode {

    // Authentication
    INVALID_PHONE_NUMBER_ERROR("AAR-AUTHN-1", BAD_REQUEST, "잘못된 전화번호 형식입니다."),
    INVALID_EMAIL_ADDRESS("AAR-AUTHN-2", BAD_REQUEST, "잘못된 이메일 형식입니다."),
    BUSINESS_LOGIN_FAILED("AAR-AUTHN-3", UNAUTHORIZED, "비즈니스 로그인에 실패했습니다."),
    EMAIL_CODE_AUTHENTICATION_FAILED("AAR-AUTHN-4", UNAUTHORIZED, "이메일 인증에 실패했습니다."),
    NOT_OWNED_HOME("AAR-AUTHN-5", FORBIDDEN, "본인이 소유하지 않은 홈에 접근했습니다."),
    NOT_OWNED_ROOM("AAR-AUTHN-6", FORBIDDEN, "본인이 소유하지 않은 룸에 접근했습니다."),
    NOT_OWNED_ITEM("AAR-AUTHN-7", FORBIDDEN, "본인이 소유하지 않은 아이템에 접근했습니다."),
    NOT_OWNED_SOUND_SOURCE("AAR-AUTHN-8", FORBIDDEN, "본인이 소유하지 않은 음원에 접근했습니다."),
    SNS_ACCOUNT_NOT_FOUND("AAR-AUTHN-9", NOT_FOUND, "SNS 계정을 찾을 수 없습니다."),

    // User
    NOT_VERIFIED_EMAIL("AAR-USER-1", UNAUTHORIZED, "확인되지 않은 이메일 주소입니다."),
    BUSINESS_USER_NOT_FOUND("AAR-USER-2", NOT_FOUND, "비즈니스 사용자를 찾을 수 없습니다."),
    BUSINESS_USER_NOT_CONFIRMED("AAR-USER-3", FORBIDDEN, "관리자의 계정 승인 대기중입니다."),
    NICK_NAME_CONFLICT("AAR-USER-3", CONFLICT, "중복된 닉네임입니다."),
    ALREADY_REGISTERED_SNS_ACCOUNT("AAR-USER-4", CONFLICT, "이미 등록된 SNS 계정입니다."),

    NOT_OWNED_PRIVATE_UNIVERSE_ACCESS("AAR-UNIVERSE-1", FORBIDDEN, "본인의 소유가 아닌 비공개 유니버스에 접근했습니다."),
    UNIVERSE_NOT_FOUND("AAR-UNIVERSE-2", NOT_FOUND, "유니버스를 찾을 수 없습니다."),

    // Common
    LOAD_ENTITY_FAILED("AAR-COMMON-1", INTERNAL_SERVER_ERROR, "객체를 불러오는데 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
