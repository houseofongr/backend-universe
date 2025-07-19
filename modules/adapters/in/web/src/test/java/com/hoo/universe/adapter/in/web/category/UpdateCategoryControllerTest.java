package com.hoo.universe.adapter.in.web.category;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.in.web.dto.result.UpdateCategoryResult;
import com.hoo.universe.api.in.web.usecase.UpdateCategoryUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateCategoryControllerTest extends DocumentationTest {

    @MockitoBean
    UpdateCategoryUseCase updateCategoryUseCase;

    @Test
    @DisplayName("카테고리 수정")
    void updateCategories() throws Exception {

        //language=JSON
        String content = """ 
                {
                  "eng" : "altered category",
                  "kor" : "변경된 카테고리"
                }
                """;

        UUID categoryID = UuidCreator.getTimeOrderedEpoch();

        when(updateCategoryUseCase.updateCategory(any(), argThat(command ->
                command.eng().equals("altered category") &&
                command.kor().equals("변경된 카테고리")
        ))).thenReturn(
                new UpdateCategoryResult(
                        categoryID,
                        "altered category",
                        "변경된 카테고리"));

        mockMvc.perform(patch("/universes/categories/{categoryID}", categoryID)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("update-category",
                        pathParameters(
                                parameterWithName("categoryID").description("수정할 카테고리의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("kor").description("수정할 한글 이름입니다. +" + "\n" +
                                                                 "* null 시 한글 수정하지 않음)").optional(),
                                fieldWithPath("eng").description("수정할 영문 이름입니다. +" + "\n" +
                                                                 "* null 시 영문 수정하지 않음)").optional()
                        ),
                        responseFields(
                                fieldWithPath("categoryID").description("수정된 카테고리의 아이디입니다."),
                                fieldWithPath("kor").description("수정된 카테고리의 한글 이름입니다."),
                                fieldWithPath("eng").description("수정된 카테고리의 영문 이름입니다.")
                        )
                ));
    }

}