package com.hoo.admin.adapter.in.web.house;

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

class PatchHouseControllerTest extends AbstractControllerTest {

    @Test
    @Sql("PatchHouseControllerTest.sql")
    @DisplayName("하우스 수정 문서화")
    void testHouseUpdateInfo() throws Exception {

        String requestBody = """
                {
                  "title" : "not cozy house",
                  "author" : "arang",
                  "description" : "this is not cozy house!!!"
                }
                """;

        mockMvc.perform(patch("/admin/houses/{houseId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-patch",
                        pathParameters(parameterWithName("houseId").description("수정할 하우스의 ID입니다.")),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다."),
                                fieldWithPath("author").description("수정할 작가명입니다."),
                                fieldWithPath("description").description("수정할 상세정보입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 하우스 정보 수정이 완료되었습니다.")
                        )
                ));
    }
}