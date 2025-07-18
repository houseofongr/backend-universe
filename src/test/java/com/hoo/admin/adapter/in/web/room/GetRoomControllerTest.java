package com.hoo.admin.adapter.in.web.room;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetRoomControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetRoomControllerTest.sql")
    @DisplayName("룸 정보 조회 API")
    void testLoadRoomData() throws Exception {
        mockMvc.perform(get("/admin/houses/rooms/{roomId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-room-get",
                        pathParameters(
                                parameterWithName("roomId").description("조회할 룸의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("name").description("룸의 이름입니다."),
                                fieldWithPath("width").description("룸의 가로 길이입니다."),
                                fieldWithPath("height").description("룸의 높이입니다."),
                                fieldWithPath("imageId").description("룸의 이미지 ID입니다.")
                        )
                ));
    }
}