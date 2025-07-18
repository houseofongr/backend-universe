package com.hoo.admin.application.service.space;

import com.hoo.admin.application.port.in.space.CreateSpaceCommand;
import com.hoo.admin.application.port.out.space.CreateSpacePort;
import com.hoo.admin.application.port.out.space.SaveSpacePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.common.application.service.MockEntityFactoryService;
import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.file.domain.FileSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateSpaceServiceTest {

    CreateSpaceService sut;

    UploadPublicImageUseCase uploadPublicImageUseCase = mock();
    CreateSpacePort createSpacePort = mock();
    SaveSpacePort saveSpacePort = mock();

    @BeforeEach
    void init() {
        sut = new CreateSpaceService(uploadPublicImageUseCase, createSpacePort, saveSpacePort);
    }

    MockMultipartFile basicImage = new MockMultipartFile("image", "image.png", "image/png", "basic image".getBytes());

    @Test
    @DisplayName("제목(100자/NB), 내용(5000자), 숨김 여부(hidden) 조건 확인")
    void testBasicInfo() {
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, emptyTitle, null, 0.5F, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, blankTitle, null, 0.5F, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, length100, null, 0.5F, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", length5000, 0.5F, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", "공간공간", 0.5F, 0.5F, 0.5F, 0.5F, null, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("위치(startX, startY, scalex, scaley : 0 ~ 1) 조건 확인")
    void testPositionInfo() {
        // null
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, null, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, null, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, null, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, null, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        // 0 미만
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, -0.5F, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, -0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, -0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, -0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        // 1 이상
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 1.5F, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 1.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 1.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, 1.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("내부사진 없거나 100MB 초과 케이스")
    void testExceed2MB() {
        // given
        CreateSpaceCommand command = new CreateSpaceCommand(1L, -1L, "공간", "", 1f, 1f, 1f, 1f, false, null);
        byte[] content = new byte[100 * 1024 * 1024 + 1];
        MockMultipartFile over100MB = new MockMultipartFile("image", "image.png", "image/png", content);
        assertThatThrownBy(() -> CreateSpaceCommand.withImageFile(command, null)).hasMessage(AdminErrorCode.SPACE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> CreateSpaceCommand.withImageFile(command, over100MB)).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("유니버스 없음")
    void noUniverseOrParentId() {
        assertThatThrownBy(() -> new CreateSpaceCommand(null, 1L, "공간", null, 1.0F, 0.5F, 0.5F, 0.5F, false, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("스페이스 생성 요청 정상 생성")
    void happyCase() {
        new CreateSpaceCommand(1L, -1L, "공간", "", 1f, 1f, 1f, 1f, false, basicImage);
    }

    @Test
    @DisplayName("스페이스 정상 생성(이미지 저장 및 스페이스 저장 포트 호출)")
    void createSpaceService() {
        // given
        MockMultipartFile basicImage = new MockMultipartFile("image", "image.png", "image/png", "basic image".getBytes());
        CreateSpaceCommand command = new CreateSpaceCommand(1L, -1L, "공간", "", 1f, 1f, 1f, 1f, false, basicImage);
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(1L, null, "image.png", "image1234.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        sut.create(command);

        // then
        verify(saveSpacePort, times(1)).save(any());
    }
}