package com.hoo.admin.adapter.in.web.space;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class PatchSpaceControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("스페이스 수정 API")
    void testSpaceUpdateAPI() throws Exception {
        //language=JSON
        String request = """
                {
                  "title": "블랙홀",
                  "description": "블랙홀은 빛도 빨아들입니다.",
                  "hidden": false
                }
                """;

        mockMvc.perform(patch("/admin/spaces/{spaceId}", 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-space-patch",
                        pathParameters(
                                parameterWithName("spaceId").description("수정할 스페이스의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다.").optional(),
                                fieldWithPath("description").description("수정할 상세정보입니다.").optional(),
                                fieldWithPath("hidden").description("수정할 숨김 여부입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : '[#id]번 스페이스의 상세정보가 수정되었습니다.'"),
                                fieldWithPath("title").description("수정된 제목입니다."),
                                fieldWithPath("description").description("수정된 상세정보입니다."),
                                fieldWithPath("hidden").description("수정된 숨김 여부입니다.")
                        )
                ));
    }


    @Test
    @DisplayName("스페이스 좌표 수정 API")
    void testSpaceUpdatePositionAPI() throws Exception {
        //language=JSON
        String request = """
                {
                  "startX": 0.6,
                  "startY": 0.7,
                  "endX": 0.5,
                  "endY": 0.4
                }
                """;

        mockMvc.perform(patch("/admin/spaces/position/{spaceId}", 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-space-patch-position",
                        pathParameters(
                                parameterWithName("spaceId").description("수정할 스페이스의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("startX").description("수정할 X축 시작좌표입니다."),
                                fieldWithPath("startY").description("수정할 Y축 시작좌표입니다."),
                                fieldWithPath("endX").description("수정할 X축 종료좌표입니다."),
                                fieldWithPath("endY").description("수정할 Y축 종료좌표입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : '[#id]번 스페이스의 좌표가 수정되었습니다.'"),
                                fieldWithPath("startX").description("수정된 X축 시작좌표입니다."),
                                fieldWithPath("startY").description("수정된 Y축 시작좌표입니다."),
                                fieldWithPath("endX").description("수정된 X축 종료좌표입니다."),
                                fieldWithPath("endY").description("수정된 Y축 종료좌표입니다.")
                        )
                ));
    }
}