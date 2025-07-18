package com.hoo.admin.adapter.in.web.universe;

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

@Sql("classpath:/sql/universe.sql")
class GetUniverseTreeControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("유니버스 내부 트리 조회 API")
    void getUniverseTreeAPI() throws Exception {
        mockMvc.perform(get("/admin/universes/tree/{universeId}", 1)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-get-tree",
                        pathParameters(
                                parameterWithName("universeId").description("조회할 유니버스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("universeId").description("유니버스의 ID입니다."),
                                fieldWithPath("innerImageId").description("유니버스의 내부 이미지파일 ID입니다."),
                                fieldWithPath("spaces").description("유니버스 내부의 스페이스입니다."),
                                fieldWithPath("pieces").description("유니버스 내부의 피스입니다."),

                                fieldWithPath("spaces[].spaceId").description("스페이스의 ID입니다."),
                                fieldWithPath("spaces[].parentSpaceId").description("스페이스의 부모 스페이스 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("spaces[].innerImageId").description("스페이스의 내부 이미지파일 ID입니다."),
                                fieldWithPath("spaces[].depth").description("스페이스의 깊이입니다."),
                                fieldWithPath("spaces[].title").description("스페이스의 제목입니다."),
                                fieldWithPath("spaces[].description").description("스페이스의 설명입니다."),
                                fieldWithPath("spaces[].hidden").description("스페이스의 숨김여부입니다."),
                                fieldWithPath("spaces[].startX").description("스페이스의 시작좌표(X)입니다."),
                                fieldWithPath("spaces[].startY").description("스페이스의 시작좌표(Y)입니다."),
                                fieldWithPath("spaces[].endX").description("스페이스의 종료좌표(X)입니다."),
                                fieldWithPath("spaces[].endY").description("스페이스의 종료좌표(Y)입니다."),
                                fieldWithPath("spaces[].createdTime").description("스페이스의 생성일자입니다.(유닉스 타임스태프)"),
                                fieldWithPath("spaces[].updatedTime").description("스페이스의 수정일자입니다.(유닉스 타임스태프)"),
                                subsectionWithPath("spaces[].pieces").description("스페이스 내부의 피스입니다."),
                                subsectionWithPath("spaces[].spaces").description("스페이스 내부의 스페이스입니다.(무한 depth)"),

                                fieldWithPath("pieces[].pieceId").description("피스의 ID입니다."),
                                fieldWithPath("pieces[].parentSpaceId").description("피스의 부모 스페이스 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("pieces[].innerImageId").description("피스의 내부 이미지파일 ID입니다."),
                                fieldWithPath("pieces[].depth").description("피스의 깊이입니다."),
                                fieldWithPath("pieces[].title").description("피스의 제목입니다."),
                                fieldWithPath("pieces[].description").description("피스의 설명입니다."),
                                fieldWithPath("pieces[].hidden").description("피스의 숨김여부입니다."),
                                fieldWithPath("pieces[].startX").description("피스의 시작좌표(X)입니다."),
                                fieldWithPath("pieces[].startY").description("피스의 시작좌표(Y)입니다."),
                                fieldWithPath("pieces[].endX").description("피스의 종료좌표(X)입니다."),
                                fieldWithPath("pieces[].endY").description("피스의 종료좌표(Y)입니다."),
                                fieldWithPath("pieces[].createdTime").description("피스의 생성일자입니다.(유닉스 타임스태프)"),
                                fieldWithPath("pieces[].updatedTime").description("피스의 수정일자입니다.(유닉스 타임스태프)")
                        )
                ));
    }
}