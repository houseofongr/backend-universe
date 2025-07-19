package com.hoo.universe.application.category;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.web.dto.result.SearchCategoryResult;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SearchCategoryServiceTest {

    QueryCategoryPort queryCategoryPort = mock();
    SearchCategoryService sut = new SearchCategoryService(queryCategoryPort);

    @Test
    @DisplayName("카테고리 조회 서비스")
    void testCategorySearchService() {
        List<Category> categories = List.of(
                new Category(UuidCreator.getTimeOrderedEpoch(), "category", "카테고리"),
                new Category(UuidCreator.getTimeOrderedEpoch(), "category2", "카테고리2")
        );

        when(queryCategoryPort.findAllCategories()).thenReturn(categories);
        SearchCategoryResult result = sut.searchAllCategories();

        assertThat(result.categories()).hasSize(2)
                .anyMatch(category -> category.getEng().equals("category") && category.getKor().equals("카테고리"))
                .anyMatch(category -> category.getEng().equals("category2") && category.getKor().equals("카테고리2"));
    }

}