package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.dto.UpdateCategoryCommand;
import com.hoo.universe.api.out.UpdateCategoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class UpdateCategoryServiceTest {

    UpdateCategoryPort updateCategoryPort = mock();
    UpdateCategoryService sut = new UpdateCategoryService(updateCategoryPort);

    @Test
    @DisplayName("카테고리 업데이트 서비스")
    void testUpdateCategoryService() {
        UUID categoryID = UuidCreator.getTimeOrderedEpoch();
        sut.updateCategory(categoryID, new UpdateCategoryCommand("업데이트", "update"));
        verify(updateCategoryPort, times(1)).updateCategory(categoryID, "업데이트", "update");
    }

}