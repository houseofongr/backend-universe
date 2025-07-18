package com.hoo.universe.adapter.in.web.category;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.category.CreateCategoryResult;
import com.hoo.universe.api.in.category.CreateCategoryUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateCategoryControllerTest extends DocumentationTest {

    @MockitoBean
    CreateCategoryUseCase createCategoryUseCase;

    @Test
    @DisplayName("카테고리 생성")
    void createCategory() throws Exception {
        //language=JSON
        String content = """ 
                {
                  "eng" : "new category",
                  "kor" : "새 카테고리"
                }
                """;

        when(createCategoryUseCase.createNewCategory(argThat(command ->
                command.eng().equals("new category") &&
                command.kor().equals("새 카테고리")
        ))).thenReturn(new CreateCategoryResult(
                UuidCreator.getTimeOrderedEpoch(),
                "new category",
                "새 카테고리"));

        mockMvc.perform(post("/universes/categories")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("create-category",
                        requestFields(
                                fieldWithPath("kor").description("생성할 카테고리의 한글 이름입니다."),
                                fieldWithPath("eng").description("생성할 카테고리의 영문 이름입니다.")
                        ),
                        responseFields(
                                fieldWithPath("categoryID").description("생성된 카테고리의 아이디입니다."),
                                fieldWithPath("kor").description("생성된 카테고리의 한글 이름입니다."),
                                fieldWithPath("eng").description("생성된 카테고리의 영문 이름입니다.")
                        )
                ));
    }

}