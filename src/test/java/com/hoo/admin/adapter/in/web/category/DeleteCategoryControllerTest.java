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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("DeleteCategoryControllerTest.sql")
class DeleteCategoryControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("카테고리 삭제 API")
    void deleteCategories() throws Exception {

        mockMvc.perform(delete("/admin/categories/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-category-delete",
                        pathParameters(
                                parameterWithName("categoryId").description("삭제할 카테고리의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : '[#id]번 카테고리가 삭제되었습니다.'"),
                                fieldWithPath("deletedCategoryId").description("삭제된 카테고리의 아이디입니다.")
                        )
                ));
    }

}