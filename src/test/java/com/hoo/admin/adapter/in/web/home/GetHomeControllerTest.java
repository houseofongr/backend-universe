package com.hoo.admin.adapter.in.web.home;

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

class GetHomeControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("조회할 홈이 없을때 테스트")
    void testNotFound() throws Exception {
        mockMvc.perform(get("/admin/homes/{homeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(404));
    }

    @Test
    @Sql("GetHomeControllerTest.sql")
    @DisplayName("홈 상세조회 테스트")
    void testGetHouse() throws Exception {
        mockMvc.perform(get("/admin/homes/{homeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-home-get",
                        pathParameters(
                                parameterWithName("homeId").description("조회할 홈의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("homeId").description("조회된 홈의 ID입니다."),
                                fieldWithPath("homeName").description("홈의 이름입니다."),
                                fieldWithPath("createdDate").description("홈이 생성된 날짜입니다."),
                                fieldWithPath("updatedDate").description("홈이 최종 수정된 날짜입니다."),
                                fieldWithPath("house.width").description("하우스의 가로 길이입니다."),
                                fieldWithPath("house.height").description("하우스의 높이입니다."),
                                fieldWithPath("house.borderImageId").description("하우스 테두리 이미지의 ID입니다."),

                                fieldWithPath("user.id").description("홈 소유자의 ID입니다."),
                                fieldWithPath("user.nickname").description("홈 소유자의 닉네임입니다."),

                                fieldWithPath("rooms[].roomId").description("룸의 ID입니다."),
                                fieldWithPath("rooms[].name").description("룸의 이름입니다."),
                                fieldWithPath("rooms[].x").description("룸의 시작점(X좌표)입니다."),
                                fieldWithPath("rooms[].y").description("룸의 시작점(Y좌표)입니다."),
                                fieldWithPath("rooms[].z").description("룸의 시작점(Z좌표)입니다."),
                                fieldWithPath("rooms[].width").description("룸의 가로 길이입니다."),
                                fieldWithPath("rooms[].height").description("룸의 높이입니다."),
                                fieldWithPath("rooms[].imageId").description("룸의 이미지 ID입니다.")
                        )));
    }

}