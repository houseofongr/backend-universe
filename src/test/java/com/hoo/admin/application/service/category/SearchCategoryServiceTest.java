package com.hoo.admin.application.service.category;

import com.hoo.admin.application.port.out.category.FindCategoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SearchCategoryServiceTest {

    FindCategoryPort findCategoryPort = mock();
    SearchCategoryService sut = new SearchCategoryService(findCategoryPort);

    @Test
    @DisplayName("카테고리 조회 서비스")
    void testCategorySearchService() {
        sut.search();
        verify(findCategoryPort, times(1)).findAll();
    }

}