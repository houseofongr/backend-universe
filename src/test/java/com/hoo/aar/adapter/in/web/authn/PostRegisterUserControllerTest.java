package com.hoo.aar.adapter.in.web.authn;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostRegisterUserControllerTest extends AbstractControllerTest {

    @Test
    @Sql("PostUserControllerTest.sql")
    @DisplayName("회원가입 API")
    void testRegister() throws Exception {

        String badBody = "{\"personalInformationAgreement\":true}";

        mockMvc.perform(post("/aar/authn/regist")
                        .content(badBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("snsId", 1L))
                                .authorities(new SimpleGrantedAuthority("ROLE_TEMP_USER")))
                )
                .andExpect(status().is(400));

        String body = "{\"termsOfUseAgreement\":true, \"personalInformationAgreement\":true}";

        mockMvc.perform(post("/aar/authn/regist")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("snsId", 1L))
                                .authorities(new SimpleGrantedAuthority("ROLE_TEMP_USER")))
                )
                .andExpect(status().is(201))
                .andDo(document("aar-authn-regist",
                        requestFields(
                                fieldWithPath("termsOfUseAgreement").description("녹화물에 대한 2차 가공 동의여부입니다."),
                                fieldWithPath("personalInformationAgreement").description("사용자 개인정보 수집 및 활용에 대한 동의여부입니다.")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("회원가입한 사용자의 ID입니다."),
                                fieldWithPath("nickname").description("회원가입한 사용자의 닉네임입니다."),
                                fieldWithPath("accessToken").description("회원가입한 사용자의 JWT 액세스 토큰입니다. +" + "\n" + "* Claims : [userId, snsId, nickname, role]"))));
    }
}
