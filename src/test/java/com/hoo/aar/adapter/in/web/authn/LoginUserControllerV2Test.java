package com.hoo.aar.adapter.in.web.authn;

import com.hoo.common.adapter.in.web.config.MockDocumentationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@MockDocumentationTest
@Import(LoginDocumentationOAuth2ControllerV2.class)
public class LoginUserControllerV2Test {

    MockMvc mockMvc;

    @BeforeEach
    void init(WebApplicationContext wac, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris().scheme("https").host("api.archiveofongr.site").removePort(), prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .build();
    }

    @Test
    @DisplayName("SNS 로그인 API 2")
    void testSnsLogin() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}/v2", "kakao")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("aar-authn-login-2",
                        pathParameters(
                                parameterWithName("provider").description("로그인을 시도할 서드파티 인증 기관명입니다. +" + "\n" + "[kakao, naver, google, apple]")),
                        responseFields(
                                fieldWithPath("nickname").description("로그인한 사용자의 닉네임입니다."),
                                fieldWithPath("accessToken").description("로그인한 사용자의 JWT 액세스 토큰입니다. +" + "\n" + "Claim : [userId, snsId, nickname, role]"),
                                fieldWithPath("provider").description("로그인을 제공한 서드파티 인증 기관명입니다. +" + "\n" + "[kakao, naver, google, apple]"),
                                fieldWithPath("isFirstLogin").description("해당 사용자가 처음 로그인했는지 여부입니다."))));
    }

}
