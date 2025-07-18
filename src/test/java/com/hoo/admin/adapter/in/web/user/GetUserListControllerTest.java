package com.hoo.admin.adapter.in.web.user;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetUserListControllerTest extends AbstractControllerTest {

    @Test
    @Sql("GetUserListControllerTest.sql")
    @DisplayName("사용자 리스트 조회 API")
    void testGetUserList() throws Exception {
        mockMvc.perform(get("/admin/users?page=1&size=9"))
                .andExpect(status().is(200))
                .andDo(document("admin-user-get-list",
                        pathParameters(
                                parameterWithName("page").description("보여줄 페이지 번호입니다. +" + "\n" + "* 기본값 : 1").optional(),
                                parameterWithName("size").description("한 페이지에 보여줄 데이터 개수입니다. +" + "\n" + "* 기본값 : 10").optional()
                        ),
                        responseFields(
                                fieldWithPath("users[].id").description("사용자의 ID입니다."),
                                fieldWithPath("users[].realName").description("사용자의 이름입니다."),
                                fieldWithPath("users[].nickName").description("사용자의 닉네임입니다."),
                                fieldWithPath("users[].phoneNumber").description("사용자의 전화번호입니다."),
                                fieldWithPath("users[].registeredDate").description("사용자의 등록일입니다(유닉스 타임스탬프)."),
                                fieldWithPath("users[].termsOfUseAgreement").description("사용자의 이용약관 동의여부입니다."),
                                fieldWithPath("users[].personalInformationAgreement").description("사용자의 개인정보 수집 및 이용 동의여부입니다."),
                                fieldWithPath("users[].snsAccounts[].domain").description("SNS 계정의 제공자입니다."),
                                fieldWithPath("users[].snsAccounts[].email").description("SNS 계정의 이메일 주소입니다."),

                                fieldWithPath("pagination.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("pagination.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("pagination.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }
}