package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.out.UpdateCategoryPort;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class DeleteCategoryServiceTest {

    QueryCategoryPort queryCategoryPort = mock();
    UpdateCategoryPort deleteCategoryPort = mock();

    DeleteCategoryService sut = new DeleteCategoryService(queryCategoryPort, deleteCategoryPort);

    @Test
    @DisplayName("카테고리 삭제 서비스")
    void testDeleteCategoryService() {
        // given
        UUID id = UuidCreator.getTimeOrderedEpoch();

        // when
        when(queryCategoryPort.findUniverseCategory(id)).thenReturn(new Category(id, "category", "카테고리"));
        sut.deleteCategory(id);

        // then
        verify(deleteCategoryPort, times(1)).deleteCategory(id);
    }

}