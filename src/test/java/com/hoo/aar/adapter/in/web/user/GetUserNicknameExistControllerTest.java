package com.hoo.aar.adapter.in.web.user;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/clear.sql")
@Sql("GetUserNicknameExistControllerTest.sql")
class GetUserNicknameExistControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("닉네임 중복여부 조회 API")
    void getUserNicknameExistAPI() throws Exception {
        mockMvc.perform(get("/aar/users/nickname/{nickname}", "leaf"))
                .andExpect(status().is(200))
                .andDo(document("aar-user-get-nickname",
                        pathParameters(
                                parameterWithName("nickname").description("존재여부를 확인할 닉네임입니다.")
                        ),
                        responseFields(
                                fieldWithPath("exist").description("해당 닉네임이 존재하는지 여부입니다.")
                        )
                ));

    }
}