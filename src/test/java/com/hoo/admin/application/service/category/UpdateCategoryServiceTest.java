package com.hoo.admin.application.service.category;

import com.hoo.admin.application.port.in.category.UpdateCategoryCommand;
import com.hoo.admin.application.port.out.category.UpdateCategoryPort;
import com.hoo.admin.application.service.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateCategoryServiceTest {

    UpdateCategoryPort updateCategoryPort = mock();
    UpdateCategoryService sut = new UpdateCategoryService(updateCategoryPort);

    @Test
    @DisplayName("입력값 검증")
    void testVerify() {
        String nullName = null;
        String empty = "";
        String blank = " ";
        String enough = "a".repeat(100);
        String tooLong = "a".repeat(101);
        sut.update(1L, new UpdateCategoryCommand(enough, enough));
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(nullName, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(empty, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(blank, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(tooLong, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(enough, nullName))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(enough, empty))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(enough, blank))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, new UpdateCategoryCommand(enough, tooLong))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("카테고리 업데이트 서비스")
    void testUpdateCategoryService() {
        sut.update(1L, new UpdateCategoryCommand("업데이트", "update"));

        verify(updateCategoryPort, times(1)).update(1L, "업데이트", "update");
    }

}