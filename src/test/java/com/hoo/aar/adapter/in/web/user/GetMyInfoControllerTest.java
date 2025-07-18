package com.hoo.aar.adapter.in.web.user;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.ownerity.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetMyInfoControllerTest extends AbstractControllerTest {

    @Test
    @Sql("GetMyInfoControllerTest.sql")
    @DisplayName("본인정보 조회 API")
    void testMyInfoAPI() throws Exception {
        mockMvc.perform(get("/aar/users/me")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-user-get-me",
                        responseFields(
                                fieldWithPath("nickname").description("본인의 닉네임입니다."),
                                fieldWithPath("email").description("본인의 기본 이메일 주소입니다."),
                                fieldWithPath("registeredDate").description("본인의 가입일자입니다."),
                                fieldWithPath("termsOfUseAgreement").description("본인의 이용약관 동의여부입니다."),
                                fieldWithPath("personalInformationAgreement").description("본인의 개인정보 활용 동의여부입니다."),
                                fieldWithPath("myHomeCount").description("보유중인 홈 개수입니다."),
                                fieldWithPath("mySoundSourceCount").description("보유중인 음원 개수입니다. +" + "\n" + "* 활성화(Active)된 음원 개수만 조회됩니다."),

                                fieldWithPath("snsAccountInfos[].domain").description("보유중인 SNS 계정의 도메인(제공 회사)입니다."),
                                fieldWithPath("snsAccountInfos[].email").description("보유중인 SNS 계정의 이메일 주소입니다.")
                        )
                ));

    }
}