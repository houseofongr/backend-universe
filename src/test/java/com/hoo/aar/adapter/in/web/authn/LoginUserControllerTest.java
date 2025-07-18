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

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockDocumentationTest
@Import(LoginDocumentationOAuth2Controller.class)
public class LoginUserControllerTest {

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
    @DisplayName("SNS 로그인 API")
    void testSnsLogin() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}", "kakao")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302))
                .andDo(document("aar-authn-login",
                        pathParameters(parameterWithName("provider").description("로그인을 시도할 서드파티 인증 기관명입니다. +" + "\n" + "[kakao, naver, google, apple]")),
                        responseHeaders(headerWithName("Location").description("로그인 이후 이동하는 경로와 쿼리 파라미터입니다."))));
    }
}
