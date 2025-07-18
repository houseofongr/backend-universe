package com.hoo.admin.adapter.in.web.user;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetUserHomesControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetUserHomesControllerTest.sql")
    @DisplayName("사용자 홈 조회 테스트")
    void testGetHomeList() throws Exception {
        mockMvc.perform(get("/admin/users/{userId}/homes", 10L))
                .andExpect(status().is(200))
                .andDo(document("admin-user-get-home-list",
                        pathParameters(
                                parameterWithName("userId").description("홈 리스트를 조회할 사용자 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("homes[].id").description("홈의 아이디입니다."),
                                fieldWithPath("homes[].name").description("홈의 이름입니다."),
                                fieldWithPath("homes[].createdDate").description("홈이 생성된 날짜입니다."),
                                fieldWithPath("homes[].updatedDate").description("홈이 수정된 날짜입니다."),

                                fieldWithPath("homes[].user.id").description("홈을 소유한 사용자의 ID입니다."),
                                fieldWithPath("homes[].user.nickname").description("홈을 소유한 사용자의 닉네임입니다."),

                                fieldWithPath("homes[].baseHouse.id").description("하우스의 ID입니다."),
                                fieldWithPath("homes[].baseHouse.title").description("하우스의 타이틀입니다."),
                                fieldWithPath("homes[].baseHouse.author").description("하우스의 작가입니다."),
                                fieldWithPath("homes[].baseHouse.description").description("하우스에 대한 설명입니다. +" + "\n" + "* 100자까지만 전송됩니다.")
                        )
                ));
    }
}