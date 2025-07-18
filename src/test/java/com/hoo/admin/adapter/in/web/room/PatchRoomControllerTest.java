package com.hoo.admin.adapter.in.web.room;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchRoomControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("룸 정보 수정 시 빈값 요청")
    void testEmptyRoomInfoExceptionHandler() throws Exception {
        mockMvc.perform(patch("/admin/houses/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(400));
    }

    @Test
    @Sql("PatchRoomControllerTest.sql")
    @DisplayName("룸 정보 수정 API")
    void testRoomInfoUpdate() throws Exception {

        String requestBody = """
                [
                    {
                      "houseDetail" : "1",
                      "newName" : "욕실"
                    }, {
                      "houseDetail" : "2",
                      "newName" : "베란다"
                    }
                ]
                """;

        mockMvc.perform(patch("/admin/houses/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-room-patch",
                        requestFields(
                                fieldWithPath("[].houseDetail").description("수정할 룸의 ID입니다."),
                                fieldWithPath("[].newName").description("수정할 이름입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0개 룸의 정보 수정이 완료되었습니다.")
                        )
                ));
    }
}