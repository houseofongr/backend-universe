package com.hoo.universe.application.exception;

import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Universe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * <pre>
 * <h1>Error Code Index</h1>
 * <ul>
 *     <li>Bad Request(400) : 1 ~ 99</li>
 *     <li>Authentication(401) : 100 ~ 199</li>
 *     <li>Not Found(404) : 300 ~ 399</li>
 *     <li>Conflict(405) : 400 ~ 499</li>
 *     <li>Internal Server Error(500) : 500 ~ 599</li>
 *     <li>Etc : 600 ~</li>
 * </ul>
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum AdapterErrorCode implements ErrorCode {

    BAD_METADATA_FORMAT("UNIVERSE-WEB-1", BAD_REQUEST, "메타데이터를 파싱할 수 없습니다."),
    GET_FILE_INPUT_STREAM_FAILED("UNIVERSE-WEB-2", BAD_REQUEST, "업로드 파일을 읽어오는데 실패했습니다."),

    POLYGON_SERIALIZE_FAILED("UNIVERSE-PRESENTATION-1", BAD_REQUEST, "다각형 객체 직렬화에 실패했습니다."),
    POLYGON_DESERIALIZE_FAILED("UNIVERSE-PRESENTATION-2", BAD_REQUEST, "다각형 객체 역직렬화에 실패했습니다."),

    UNIVERSE_NOT_FOUND("UNIVERSE-PRESENTATION-300", NOT_FOUND, "해당 유니버스를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND("UNIVERSE-PRESENTATION-301", NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),
    SPACE_NOT_FOUND("UNIVERSE-PRESENTATION-302", NOT_FOUND, "해당 스페이스를 찾을 수 없습니다."),
    PIECE_NOT_FOUND("UNIVERSE-PRESENTATION-303", NOT_FOUND, "해당 피스를 찾을 수 없습니다."),
    SOUND_NOT_FOUND("UNIVERSE-PRESENTATION-304", NOT_FOUND, "해당 사운드를 찾을 수 없습니다."),
    USER_NOT_FOUND("UNIVERSE-PRESENTATION-305", NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),

    // internal server error : 1000 ~
    LOAD_ENTITY_OVERLAPPED("UNIVERSE-PRESENTATION-500", INTERNAL_SERVER_ERROR, "객체를 불러오던 중 내부의 다른 요소와 충돌했습니다."),
    LOAD_ENTITY_MAX_CHILD_EXCEED("UNIVERSE-PRESENTATION-501", INTERNAL_SERVER_ERROR, String.format("객체를 불러오던 중 자식 요소의 최대치를 초과했습니다. * 최대 자식 요소 : [%d]개", Universe.MAX_CHILD_SIZE)),
    LOAD_ENTITY_MAX_SPACE_EXCEED("UNIVERSE-PRESENTATION-502", INTERNAL_SERVER_ERROR, String.format("객체를 불러오던 중 스페이스 개수 최대치를 초과했습니다. * 최대 내부 스페이스 : [%d]개", Universe.MAX_SPACE_SIZE)),
    LOAD_ENTITY_MAX_SOUND_EXCEED("UNIVERSE-PRESENTATION-503", INTERNAL_SERVER_ERROR, String.format("객체를 불러오던 중 사운드 개수 최대치를 초과했습니다. * 최대 내부 사운드 : [%d]개", Piece.MAX_SOUND_SIZE)),
    LOAD_ENTITY_FAILED_BY_DOMAIN_ID("UNIVERSE-PRESENTATION-504", INTERNAL_SERVER_ERROR, "도메인 객체의 ID를 통해 엔티티를 불러오는데 실패했습니다."), 
    
    MESSAGE_MAPPING_FAILED("UNIVERSE-INTERNAL-MESSAGE-505", INTERNAL_SERVER_ERROR, "메시지를 매핑하는데 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
