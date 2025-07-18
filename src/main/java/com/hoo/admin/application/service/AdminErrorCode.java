package com.hoo.admin.application.service;

import com.hoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements ErrorCode {

    // House
    AXIS_PIXEL_LIMIT_EXCEED("ADMIN-HOUSE-1", BAD_REQUEST, "좌표 픽셀 크기 한도를 초과했습니다."),
    AREA_SIZE_LIMIT_EXCEED("ADMIN-HOUSE-2", BAD_REQUEST, "이미지 크기 한도를 초과했습니다."),
    INVALID_REQUEST_TYPE("ADMIN-HOUSE-3", BAD_REQUEST, "하우스 생성 간 첨부파일을 읽을 수 없습니다.(Multipart FormData 헤더가 아닙니다.)"),
    DUPLICATE_HOUSE_PROFILE_IMAGE_AND_BORDER_IMAGE("ADMIN-HOUSE-4", BAD_REQUEST, "하우스 프로필 이미지와 테두리 이미지가 중복됩니다."),
    IMAGE_FILE_NOT_FOUND("ADMIN-HOUSE-5", BAD_REQUEST, "하우스 생성 메타데이터에 등록된 이미지가 존재하지 않습니다."),
    HOLDING_HOME_HOUSE_DELETE("ADMIN-HOUSE-6", BAD_REQUEST, "홈을 보유한 하우스는 삭제할 수 없습니다."),
    HOUSE_NOT_FOUND("ADMIN-HOUSE-7", NOT_FOUND, "해당 하우스를 찾을 수 없습니다."),

    // Room
    HOLDING_ITEM_ROOM_DELETE("ADMIN-ROOM-1", BAD_REQUEST, "아이템을 보유한 룸은 삭제할 수 없습니다."),
    ROOM_NOT_FOUND("ADMIN-ROOM-2", NOT_FOUND, "해당 룸을 찾을 수 없습니다."),

    // User
    USER_NOT_FOUND("ADMIN-USER-1", NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),

    // Home
    HOME_NOT_FOUND("ADMIN-HOME-1", NOT_FOUND, "해당 홈을 찾을 수 없습니다."),
    ALREADY_CREATED_HOME("ADMIN-HOME-2", CONFLICT, "이미 동일한 요청으로 생성된 홈이 존재합니다."),
    ILLEGAL_HOME_NAME_FORMAT("ADMIN-HOME-3", BAD_REQUEST, "잘못된 홈 이름 형식입니다."),

    // Item
    ILLEGAL_SHAPE_TYPE("ADMIN-ITEM-1", BAD_REQUEST, "잘못된 아이템 형태입니다."),
    ITEM_HAS_SOUND_SOURCE("ADMIN-ITEM-2", BAD_REQUEST, "음원이 포함된 아이템은 해당 요청을 수행할 수 없습니다."),
    ITEM_NOT_FOUND("ADMIN-ITEM-3", NOT_FOUND, "해당 아이템을 찾을 수 없습니다."),

    // SoundSource
    SOUND_SOURCE_NOT_FOUND("ADMIN-SOUNDSOURCE-1", NOT_FOUND, "해당 음원을 찾을 수 없습니다."),

    // Category
    CATEGORY_NOT_FOUND("ADMIN-CATEGORY-1", NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),

    // Universe
    UNIVERSE_FILE_REQUIRED("ADMIN-UNIVERSE-1", BAD_REQUEST, "유니버스의 필수 파일이 누락되었습니다."),
    UNIVERSE_NOT_FOUND("ADMIN-UNIVERSE-2", NOT_FOUND, "해당 유니버스를 찾을 수 없습니다."),

    // Space
    SPACE_FILE_REQUIRED("ADMIN-SPACE-1", BAD_REQUEST, "스페이스의 필수 파일이 누락되었습니다."),
    SPACE_NOT_FOUND("ADMIN-SPACE-2", NOT_FOUND, "해당 스페이스를 찾을 수 없습니다."),

    // Piece
    PIECE_NOT_FOUND("ADMIN-PIECE-1", NOT_FOUND, "해당 피스를 찾을 수 없습니다."),

    // Sound
    SOUND_FILE_REQUIRED("ADMIN-SOUND-1", BAD_REQUEST, "사운드의 필수 파일이 누락되었습니다."),
    SOUND_NOT_FOUND("ADMIN-SOUND-2", NOT_FOUND, "해당 사운드를 찾을 수 없습니다."),

    // Common
    ILLEGAL_ARGUMENT_EXCEPTION("ADMIN-COMMON-1", BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    INVALID_SEARCH_TYPE("ADMIN-COMMON-2", BAD_REQUEST, "잘못된 검색 조건입니다."),
    EXCEEDED_FILE_SIZE("ADMIN-COMMON-3", BAD_REQUEST, "허용된 파일 크기를 초과했습니다."),
    LOAD_ENTITY_FAILED("ADMIN-COMMON-4", INTERNAL_SERVER_ERROR, "객체를 불러오는데 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
