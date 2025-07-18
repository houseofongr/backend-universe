package com.hoo.admin.adapter.in.web.user;

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
@Sql("PostConfirmBusinessUserControllerTest.sql")
class PostConfirmBusinessUserControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("비즈니스 회원가입 승인 API")
    void testRegister() throws Exception {

        mockMvc.perform(post("/admin/users/business/confirm")
                        .param("tempUserId", "1")
                )
                .andExpect(status().is(201))
                .andDo(document("admin-post-confirm-user-business",
                        formParameters(
                                parameterWithName("tempUserId").description("승인할 비즈니스 회원의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("회원가입 완료 메시지 +" + "\n" +
                                                                     "* '비즈니스 사용자가 등록되어 [#id]번 사용자가 생성되었습니다.'"),
                                fieldWithPath("userId").description("회원가입된 사용자의 ID입니다."),
                                fieldWithPath("email").description("회원가입된 사용자의 이메일입니다."),
                                fieldWithPath("nickname").description("회원가입된 사용자의 닉네임입니다."),
                                fieldWithPath("termsOfUseAgreement").description("회원가입된 사용자의 이용약관 동의여부입니다."),
                                fieldWithPath("personalInformationAgreement").description("회원가입된 사용자의 개인정보 활용 동의여부입니다.")
                        )
                ));
    }

}