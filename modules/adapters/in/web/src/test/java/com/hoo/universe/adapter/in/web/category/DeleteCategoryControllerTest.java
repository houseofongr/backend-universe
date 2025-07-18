package com.hoo.universe.adapter.in.web.category;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.category.DeleteCategoryResult;
import com.hoo.universe.api.in.category.DeleteCategoryUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteCategoryControllerTest extends DocumentationTest {

    @MockitoBean
    DeleteCategoryUseCase deleteCategoryUseCase;

    @Test
    @DisplayName("카테고리 삭제 API")
    void deleteCategories() throws Exception {

        UUID categoryID = UuidCreator.getTimeOrderedEpoch();

        when(deleteCategoryUseCase.deleteCategory(categoryID))
                .thenReturn(new DeleteCategoryResult(
                        categoryID,
                        "deleted category",
                        "삭제된 카테고리"));

        mockMvc.perform(delete("/universes/categories/{categoryID}", categoryID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("delete-category",
                        pathParameters(
                                parameterWithName("categoryID").description("삭제할 카테고리의 ID입니다.")
                        ),
                        responseFields(

                                fieldWithPath("categoryID").description("삭제된 카테고리의 아이디입니다."),
                                fieldWithPath("kor").description("삭제된 카테고리의 한글 이름입니다."),
                                fieldWithPath("eng").description("삭제된 카테고리의 영문 이름입니다.")
                        )
                ));
    }
}