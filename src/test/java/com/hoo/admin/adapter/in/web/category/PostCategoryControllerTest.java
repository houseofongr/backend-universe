package com.hoo.admin.adapter.in.web.category;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostCategoryControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("카테고리 생성 API")
    void postCategoryAPI() throws Exception {

        //language=JSON
        String content = """ 
                {
                  "kor" : "새 카테고리",
                  "eng" : "new category"
                }
                """;

        mockMvc.perform(post("/admin/categories")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-category-post",
                        requestFields(
                                fieldWithPath("kor").description("생성할 카테고리의 한글 이름입니다."),
                                fieldWithPath("eng").description("생성할 카테고리의 영문 이름입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : '[#id]번 카테고리가 생성되었습니다.'"),
                                fieldWithPath("categoryId").description("생성된 카테고리의 아이디입니다."),
                                fieldWithPath("kor").description("생성된 카테고리의 한글 이름입니다."),
                                fieldWithPath("eng").description("생성된 카테고리의 영문 이름입니다.")
                        )
                ));
    }
}