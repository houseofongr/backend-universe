package com.hoo.admin.adapter.in.web.house;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetHouseListControllerTest extends AbstractControllerTest {

    @Test
    @Sql("GetHouseListControllerTest.sql")
    @DisplayName("하우스 리스트 조회 API")
    void testGetList() throws Exception {
        mockMvc.perform(get("/admin/houses?page=1&size=9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-get-list",
                        pathParameters(
                                parameterWithName("page").description("보여줄 페이지 번호입니다. +" + "\n" + "* 기본값 : 1").optional(),
                                parameterWithName("size").description("한 페이지에 보여줄 데이터 개수입니다. +" + "\n" + "* 기본값 : 10").optional(),
                                parameterWithName("searchType").description("검색 방법입니다.").optional(),
                                parameterWithName("keyword").description("검색 키워드입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("houses[].id").description("하우스의 아이디입니다."),
                                fieldWithPath("houses[].title").description("하우스의 제목입니다."),
                                fieldWithPath("houses[].author").description("하우스의 작가입니다."),
                                fieldWithPath("houses[].description").description("하우스에 대한 설명입니다. +" + "\n" + "* 100자까지만 전송됩니다."),
                                fieldWithPath("houses[].createdDate").description("하우스가 생성된 날짜입니다."),
                                fieldWithPath("houses[].updatedDate").description("하우스가 최종 수정된 날짜입니다."),
                                fieldWithPath("houses[].imageId").description("하우스 기본 이미지의 ID입니다."),

                                fieldWithPath("pagination.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("pagination.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("pagination.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }

}
