package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.port.out.cache.LoadEmailAuthnStatePort;
import com.hoo.aar.application.port.out.cache.SaveEmailAuthnCodePort;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostVerifyEmailAuthnCodeControllerTest extends AbstractControllerTest {

    @Autowired
    SaveEmailAuthnCodePort saveEmailAuthnCodePort;

    @Autowired
    LoadEmailAuthnStatePort loadEmailAuthnStatePort;

    @BeforeEach
    void clearRedis() {
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Test
    @DisplayName("이메일 인증코드 생성 API")
    void testPostEmailAuthnAPI() throws Exception {

        String email = "test@example.com";
        String code = "123456";

        saveEmailAuthnCodePort.saveEmailAuthnCode(email, code, Duration.ofMinutes(5));
        assertThat(loadEmailAuthnStatePort.loadAuthenticated(email)).isFalse();

        mockMvc.perform(post("/aar/authn/email-code/verify")
                        .param("email", email)
                        .param("code", code)
                )
                .andExpect(status().is(200))
                .andDo(document("aar-authn-post-email-code-verify",
                        formParameters(
                                parameterWithName("email").description("인증을 확인할 메일주소입니다."),
                                parameterWithName("code").description("인증코드입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("인증 완료 메시지 : '이메일 인증에 성공했습니다.'"),
                                fieldWithPath("ttl").description("인증 유효시간(초)")
                        )
                ));

        assertThat(loadEmailAuthnStatePort.loadAuthenticated(email)).isTrue();
    }

}