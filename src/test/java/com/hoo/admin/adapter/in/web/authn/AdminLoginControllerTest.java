package com.hoo.admin.adapter.in.web.authn;

import com.hoo.common.adapter.in.web.config.MockDocumentationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockDocumentationTest
@Import(LoginDocumentationController.class)
public class AdminLoginControllerTest {

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
    @DisplayName("관리자페이지 로그인 API")
    void testAdminLoginAPI() throws Exception {
        mockMvc.perform(post("/admin/authn/login")
                        .param("username", "admin ID")
                        .param("password", "admin Password"))
                .andExpect(status().is(302))
                .andDo(document("admin-authn-login",
                        formParameters(
                                parameterWithName("username").description("로그인하는 관리자 계정 ID입니다."),
                                parameterWithName("password").description("로그인하는 관리자 계정 Password입니다.")
                        ),
                        responseHeaders(
                                headerWithName("location").description("로그인 시도 이후 이동하는 URL입니다. +" + "\n" + "* 성공 시 : /login?form-success, 실패 시 : /login?failure")
                        )
                ));
    }
}
