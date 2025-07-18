package com.hoo.admin.adapter.in.web.piece;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class GetPieceControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("특정 피스 내부 조회(관리자) API")
    void testGetSpecificPiece() throws Exception {

        mockMvc.perform(get("/admin/pieces/{pieceId}?page=1&size=10", 4)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-piece-get",
                        pathParameters(
                                parameterWithName("pieceId").description("조회할 피스의 ID입니다."),
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
                                fieldWithPath("pieceId").description("피스의 ID입니다."),
                                fieldWithPath("title").description("피스의 제목입니다."),
                                fieldWithPath("description").description("피스의 설명입니다."),
                                fieldWithPath("hidden").description("피스의 숨김 여부입니다."),
                                fieldWithPath("createdTime").description("피스의 생성(등록)일자입니다.(유닉스 타임스탬프)"),
                                fieldWithPath("updatedTime").description("피스의 수정일자입니다.(유닉스 타임스탬프)"),
                                fieldWithPath("sounds").description("피스 내부에 존재하는 사운드 리스트입니다."),
                                fieldWithPath("sounds[].soundId").description("사운드의 ID입니다."),
                                fieldWithPath("sounds[].audioId").description("오디오 파일의 ID입니다."),
                                fieldWithPath("sounds[].title").description("사운드의 제목입니다."),
                                fieldWithPath("sounds[].description").description("사운드의 설명입니다."),
                                fieldWithPath("sounds[].hidden").description("사운드의 숨김여부입니다."),
                                fieldWithPath("sounds[].createdTime").description("사운드의 생성(등록)일자입니다.(유닉스 타임스탬프)"),
                                fieldWithPath("sounds[].updatedTime").description("사운드의 수정일자입니다.(유닉스 타임스탬프)"),

                                fieldWithPath("pagination.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("pagination.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("pagination.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }
}