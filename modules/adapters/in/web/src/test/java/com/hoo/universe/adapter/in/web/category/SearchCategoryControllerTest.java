package com.hoo.universe.adapter.in.web.category;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.in.web.dto.result.SearchCategoryResult;
import com.hoo.universe.api.in.web.usecase.SearchCategoryUseCase;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchCategoryControllerTest extends DocumentationTest {

    @MockitoBean
    SearchCategoryUseCase searchCategoryUseCase;

    @Test
    @DisplayName("카테고리 검색")
    void searchCategories() throws Exception {

        when(searchCategoryUseCase.searchAllCategories())
                .thenReturn(new SearchCategoryResult(
                        List.of(new Category(UuidCreator.getTimeOrderedEpoch(), "Category1", "카테고리1"),
                                new Category(UuidCreator.getTimeOrderedEpoch(), "Category2", "카테고리2"),
                                new Category(UuidCreator.getTimeOrderedEpoch(), "Category3", "카테고리3"))
                ));

        mockMvc.perform(get("/universes/categories"))
                .andExpect(status().is(200))
                .andDo(document("search-category",
                        responseFields(
                                fieldWithPath("categories[].id").description("조회된 카테고리의 아이디입니다."),
                                fieldWithPath("categories[].kor").description("조회된 카테고리의 한글 이름입니다."),
                                fieldWithPath("categories[].eng").description("조회된 카테고리의 영문 이름입니다.")
                        )
                ));
    }

}