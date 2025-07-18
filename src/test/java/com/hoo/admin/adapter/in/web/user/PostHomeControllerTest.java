package com.hoo.admin.adapter.in.web.user;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostHomeControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("Home 생성 시 ID 예외처리")
    void testValidateId() throws Exception {

        String nullId = """
                {
                  "houseId": 20
                }
                """;

        mockMvc.perform(post("/admin/homes")
                        .content(nullId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));

        String notFoundId = """
                {
                  "userId": -1,
                  "houseId": 20
                }
                """;

        mockMvc.perform(post("/admin/homes")
                        .content(notFoundId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @Sql("PostHomeControllerTest.sql")
    @DisplayName("Home 생성 테스트")
    void testCreateHome() throws Exception {

        String content = """
                {
                  "userId": 10,
                  "houseId": 20
                }
                """;

        mockMvc.perform(post("/admin/homes")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(document("admin-home-post",
                        requestFields(
                                fieldWithPath("userId").description("홈을 생성할 사용자의 ID입니다."),
                                fieldWithPath("houseId").description("홈의 템플릿으로 사용할 하우스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("createdHomeId").description("생성된 홈의 아이디입니다."),
                                fieldWithPath("createdHomeName").description("생성된 홈의 이름입니다.")
                        )
                ));
    }
}