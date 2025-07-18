package com.hoo.aar.adapter.in.web.user;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/clear.sql")
@Sql("PostLoginBusinessUserControllerTest.sql")
class PostLoginBusinessUserControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("비즈니스 로그인 API")
    void testRegister() throws Exception {

        String email = "test@example.com";
        String password = "test2143@";

        mockMvc.perform(post("/aar/authn/business/login")
                        .param("email", email)
                        .param("password", password)
                )
                .andExpect(status().is(200))
                .andDo(document("aar-post-login-user-business",
                        formParameters(
                                parameterWithName("email").description("비즈니스 사용자의 이메일입니다."),
                                parameterWithName("password").description("비즈니스 사용자의 비밀번호입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("로그인 완료 메시지 +" + "\n" +
                                                                     "* '[#id]번 사용자가 로그인 되었습니다'"),
                                fieldWithPath("userId").description("로그인된 사용자의 ID입니다."),
                                fieldWithPath("email").description("로그인된 사용자의 이메일입니다."),
                                fieldWithPath("nickname").description("로그인된 사용자의 닉네임입니다."),
                                fieldWithPath("accessToken").description("로그인된 사용자의 액세스 토큰입니다.")
                        )
                ));
    }
}