package com.hoo.admin.adapter.in.web.category;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("PatchCategoryControllerTest.sql")
class PatchCategoryControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("카테고리 수정 API")
    void patchCategories() throws Exception {

        //language=JSON
        String content = """ 
                {
                  "kor" : "변경된 카테고리",
                  "eng" : "altered category"
                }
                """;

        mockMvc.perform(patch("/admin/categories/{categoryId}", 1L)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-category-patch",
                        pathParameters(
                                parameterWithName("categoryId").description("수정할 카테고리의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("kor").description("수정할 한글 이름입니다.(Null 시 한글 수정하지 않음)").optional(),
                                fieldWithPath("eng").description("수정할 영문 이름입니다.(Null 시 영문 수정하지 않음)").optional()
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : '[#id]번 카테고리가 수정되었습니다.'"),
                                fieldWithPath("categoryId").description("수정된 카테고리의 아이디입니다."),
                                fieldWithPath("kor").description("수정된 카테고리의 한글 이름입니다."),
                                fieldWithPath("eng").description("수정된 카테고리의 영문 이름입니다.")
                        )
                ));
    }
}