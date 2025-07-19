package com.hoo.universe.adapter.in.web.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.in.web.dto.result.OpenPieceResult;
import com.hoo.universe.api.in.web.usecase.OpenPieceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OpenPieceControllerTest extends DocumentationTest {

    @Autowired
    private OpenPieceUseCase openPieceUseCase;

    @Test
    @DisplayName("피스 열기")
    void openPiece() throws Exception {

        UUID pieceID = UuidCreator.getTimeOrderedEpoch();

        when(openPieceUseCase.openPieceWithSounds(any(), any()))
                .thenReturn(new OpenPieceResult(
                        pieceID,
                        "조회된 피스 제목",
                        "조회된 피스 내용",
                        false,
                        ZonedDateTime.now().toEpochSecond(),
                        ZonedDateTime.now().toEpochSecond(),
                        PageQueryResult.from(
                                PageRequest.defaultPage(),
                                1L,
                                List.of(new OpenPieceResult.SoundInfo(
                                        UuidCreator.getTimeOrderedEpoch(),
                                        URI.create("http://example.com/files/audio.mp3"),
                                        "조회된 사운드 제목",
                                        "조회된 사운드 내용",
                                        false,
                                        ZonedDateTime.now().toEpochSecond(),
                                        ZonedDateTime.now().toEpochSecond()
                                ))
                        )
                ));

        mockMvc.perform(get("/universes/pieces/{pieceID}?page=1&size=10", pieceID))
                .andExpect(status().is(200))
                .andDo(document("open-piece",
                        pathParameters(
                                parameterWithName("pieceID").description("조회할 피스의 ID입니다."),
                                parameterWithName("page").description("보여줄 페이지 번호입니다. +"
                                                                      + "\n" + "* 기본값 : 1").optional(),
                                parameterWithName("size").description("한 페이지에 보여줄 데이터 개수입니다. +"
                                                                      + "\n" + "* 기본값 : 10").optional(),
                                parameterWithName("sortType").description("정렬 방법입니다. +"
                                                                          + "\n" + "[TITLE : 제목 순] +"
                                                                          + "\n" + "[REGISTERED_DATE : 생성시간 순] +"
                                                                          + "\n" + "* 기본 정렬 : 생성시간 내림차순").optional(),
                                parameterWithName("isAsc").description("정렬 시 오름차순인지 여부입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("pieceID").description("피스의 ID입니다."),
                                fieldWithPath("title").description("피스의 제목입니다."),
                                fieldWithPath("description").description("피스의 설명입니다."),
                                fieldWithPath("hidden").description("피스의 숨김 여부입니다."),
                                fieldWithPath("createdTime").description("피스의 생성(등록)일자입니다."),
                                fieldWithPath("updatedTime").description("피스의 수정일자입니다."),
                                fieldWithPath("sounds").description("피스 내부에 존재하는 사운드 리스트입니다."),
                                fieldWithPath("sounds.content[].soundID").description("사운드의 ID입니다."),
                                fieldWithPath("sounds.content[].audioFileUrl").description("오디오 파일의 ID입니다."),
                                fieldWithPath("sounds.content[].title").description("사운드의 제목입니다."),
                                fieldWithPath("sounds.content[].description").description("사운드의 설명입니다."),
                                fieldWithPath("sounds.content[].hidden").description("사운드의 숨김여부입니다."),
                                fieldWithPath("sounds.content[].createdTime").description("사운드의 생성(등록)일자입니다."),
                                fieldWithPath("sounds.content[].updatedTime").description("사운드의 수정일자입니다."),

                                fieldWithPath("sounds.pagination.page").description("현재 페이지 번호입니다."),
                                fieldWithPath("sounds.pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("sounds.pagination.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("sounds.pagination.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }

}