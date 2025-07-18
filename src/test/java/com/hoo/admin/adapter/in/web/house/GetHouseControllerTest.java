package com.hoo.admin.adapter.in.web.house;

import com.hoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.util.GsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetHouseControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetHouseControllerTest.sql")
    @DisplayName("하우스 상세조회 테스트")
    void testGetHouse() throws Exception {
        String response = mockMvc.perform(get("/admin/houses/{houseId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-get",
                        pathParameters(
                                parameterWithName("houseId").description("조회할 하우스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("house.houseId").description("하우스의 ID입니다."),
                                fieldWithPath("house.title").description("하우스의 제목입니다."),
                                fieldWithPath("house.author").description("하우스의 작가입니다."),
                                fieldWithPath("house.description").description("하우스에 대한 설명입니다. +" + "\n" + "* 100자까지만 전송됩니다."),
                                fieldWithPath("house.createdDate").description("하우스가 생성된 날짜입니다."),
                                fieldWithPath("house.updatedDate").description("하우스가 최종 수정된 날짜입니다."),
                                fieldWithPath("house.borderImageId").description("하우스 테두리 이미지의 ID입니다."),
                                fieldWithPath("house.width").description("하우스의 가로 길이입니다."),
                                fieldWithPath("house.height").description("하우스의 높이입니다."),
                                fieldWithPath("rooms[].roomId").description("룸의 ID입니다."),
                                fieldWithPath("rooms[].name").description("룸의 이름입니다."),
                                fieldWithPath("rooms[].x").description("룸의 시작점(X좌표)입니다."),
                                fieldWithPath("rooms[].y").description("룸의 시작점(Y좌표)입니다."),
                                fieldWithPath("rooms[].z").description("룸의 시작점(Z좌표)입니다."),
                                fieldWithPath("rooms[].width").description("룸의 가로 길이입니다."),
                                fieldWithPath("rooms[].height").description("룸의 높이입니다."),
                                fieldWithPath("rooms[].imageId").description("룸의 이미지 ID입니다.")
                        ))).andReturn().getResponse().getContentAsString();

        QueryHouseResult result = GsonUtil.gson.fromJson(response, QueryHouseResult.class);

        assertThat(result.house().borderImageId()).isEqualTo(2);
    }
}