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
public enum DomainErrorCode implements ErrorCode {

    OVERLAPPED("UNIVERSE-DOMAIN-1", BAD_REQUEST, "내부의 다른 요소와 충돌했습니다."),
    MAX_CHILD_EXCEED("UNIVERSE-DOMAIN-2", BAD_REQUEST, String.format("자식 요소의 최대치를 초과했습니다. * 최대 자식 요소 : [%d]개", Universe.MAX_CHILD_SIZE)),
    MAX_SPACE_EXCEED("UNIVERSE-DOMAIN-3", BAD_REQUEST, String.format("스페이스 개수 최대치를 초과했습니다. * 최대 내부 스페이스 : [%d]개", Universe.MAX_SPACE_SIZE)),
    MAX_SOUND_EXCEED("UNIVERSE-DOMAIN-4", BAD_REQUEST, String.format("사운드 개수 최대치를 초과했습니다. * 최대 내부 사운드 : [%d]개", Piece.MAX_SOUND_SIZE));

    private final String code;
    private final HttpStatus status;
    private final String message;

}
