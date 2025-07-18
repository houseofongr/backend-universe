package com.hoo.aar.adapter.in.web.authn;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostEmailAuthnCodeControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("이메일 인증코드 생성 API")
    void testPostEmailAuthnAPI() throws Exception {

        String email = "test@example.com";
        mockMvc.perform(post("/aar/authn/email-code")
                        .param("email", email)
                )
                .andExpect(status().is(201))
                .andDo(document("aar-authn-post-email-code",
                        formParameters(
                                parameterWithName("email").description("인증코드를 전송할 메일주소입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("전송 완료 메시지 : '[이메일 : address]로 인증코드 전송이 완료되었습니다.'"),
                                fieldWithPath("ttl").description("인증 코드 유효기간(초)")
                        )
                ));
    }
}