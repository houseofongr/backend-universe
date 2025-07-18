package com.hoo.aar.adapter.in.web.user;

import com.hoo.aar.application.port.out.cache.SaveEmailAuthnStatePort;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.Duration;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostBusinessUserControllerTest extends AbstractControllerTest {

    @Autowired
    SaveEmailAuthnStatePort saveEmailAuthnStatePort;

    @Test
    @DisplayName("비즈니스 회원가입 API")
    void testRegister() throws Exception {

        saveEmailAuthnStatePort.saveAuthenticated("test@example.com", Duration.ofSeconds(10));

        //language=JSON
        String body = """
                     { 
                        "email": "test@example.com", 
                        "password": "temp2143@",
                        "nickname": "temp_user123",
                        "termsOfUseAgreement": true,
                        "personalInformationAgreement": true
                     }
                """;

        mockMvc.perform(post("/aar/users/business")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(201))
                .andDo(document("aar-post-user-business",
                        requestFields(
                                fieldWithPath("email").description("비즈니스 사용자의 이메일입니다. +" + "\n" +
                                                                   "* 인증 완료된 이메일만 가능합니다."),
                                fieldWithPath("password").description("사용할 비밀번호입니다."),
                                fieldWithPath("nickname").description("사용할 닉네임입니다."),
                                fieldWithPath("termsOfUseAgreement").description("이용약관 동의 여부입니다."),
                                fieldWithPath("personalInformationAgreement").description("개인정보 제공 동의여부입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("임시 회원가입 완료 메시지 +" + "\n" +
                                                                     "* '[#id]번 임시 사용자가 생성되었습니다. 관리자 승인 후 계정이 등록됩니다.'"),
                                fieldWithPath("tempUserId").description("임시 회원가입된 사용자의 ID입니다."),
                                fieldWithPath("email").description("회원가입한 사용자의 이메일입니다."),
                                fieldWithPath("nickname").description("회원가입한 사용자의 닉네임입니다."),
                                fieldWithPath("termsOfUseAgreement").description("회원가입한 사용자의 이용약관 동의 여부입니다."),
                                fieldWithPath("personalInformationAgreement").description("회원가입한 사용자의 개인정보 제공 동의여부입니다.")
                        )
                ));
    }
}