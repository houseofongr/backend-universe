package com.hoo.admin.application.service.category;

import com.hoo.admin.application.port.in.category.CreateCategoryCommand;
import com.hoo.admin.application.port.out.category.SaveCategoryPort;
import com.hoo.admin.application.service.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCategoryServiceTest {

    SaveCategoryPort saveCategoryPort = mock();
    CreateCategoryService sut = new CreateCategoryService(saveCategoryPort);

    @Test
    @DisplayName("입력값 검증")
    void testVerify() {
        String nullName = null;
        String empty = "";
        String blank = " ";
        String enough = "a".repeat(100);
        String tooLong = "a".repeat(101);
        sut.create(new CreateCategoryCommand(enough, enough));
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(nullName, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(empty, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(blank, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(tooLong, enough))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(enough, nullName))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(enough, empty))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(enough, blank))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(new CreateCategoryCommand(enough, tooLong))).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("카테고리 생성 서비스")
    void testCreateCategoryService() {
        String kor = "새 카테고리";
        String eng = "new category";

        sut.create(new CreateCategoryCommand(kor, eng));

        verify(saveCategoryPort, times(1)).save(any(), any());
    }

}