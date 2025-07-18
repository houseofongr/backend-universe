package com.hoo.admin.application.service.universe;

import com.hoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.hoo.admin.application.port.out.category.FindCategoryPort;
import com.hoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.admin.application.port.out.universe.UpdateUniversePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.PublicStatus;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.admin.domain.universe.UniverseCategory;
import com.hoo.common.application.service.MockEntityFactoryService;
import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicAudioUseCase;
import com.hoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.file.domain.FileSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateUniverseServiceTest {

    UpdateUniverseService sut;

    FindUniversePort findUniversePort = mock();
    FindCategoryPort findCategoryPort = mock();
    UploadPublicImageUseCase uploadPublicImageUseCase = mock();
    UploadPublicAudioUseCase uploadPublicAudioUseCase = mock();
    DeleteFileUseCase deleteFileUseCase = mock();
    UpdateUniversePort updateUniversePort = mock();

    @BeforeEach
    void init() {
        sut = new UpdateUniverseService(findUniversePort, findCategoryPort, uploadPublicImageUseCase, uploadPublicAudioUseCase, deleteFileUseCase, updateUniversePort);
    }

    @Test
    @DisplayName("제목(100자/NB) 조건 확인")
    void testTitleCondition() {
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);

        assertThatThrownBy(() -> new UpdateUniverseCommand(emptyTitle, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(blankTitle, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(length100, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("내용(5000자) 조건 확인")
    void testDescriptionCondition() {
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new UpdateUniverseCommand("우주", length5000, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("태그개수 확인하기(10개, 500자)")
    void testTagCondition() {
        List<String> tag11 = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        List<String> tagLength500 = List.of("a".repeat(501));

        assertThatThrownBy(() -> new UpdateUniverseCommand(null, null, null, null, null, tag11)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(null, null, null, null, null, tagLength500)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("정상 요청")
    void happyCase() {
        UpdateUniverseCommand command = new UpdateUniverseCommand("오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", 1L, 1L, PublicStatus.PRIVATE, List.of("오르트구름", "태양계", "윤하", "별"));

        assertThat(command).isNotNull();
    }

    @Test
    @DisplayName("수정 전 파일 확인")
    void testFile() {
        // given
        byte[] over2MB = new byte[2 * 1024 * 1024 + 1];
        byte[] over100MB = new byte[100 * 1024 * 1024 + 1];
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", over2MB);
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_thumb.mp3", "audio/mpeg", over2MB);
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_image.png", "image/png", over100MB);
        assertThatThrownBy(() -> sut.updateThumbnail(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateThumbMusic(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateInnerImage(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateThumbnail(1L, thumbnail)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> sut.updateThumbMusic(1L, thumbMusic)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> sut.updateInnerImage(1L, innerImage)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("정보 수정 서비스")
    void testUpdateDetail() {
        // given
        UpdateUniverseCommand command = new UpdateUniverseCommand("오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", 1L, 1L, PublicStatus.PRIVATE, List.of("오르트구름", "태양계", "윤하", "별"));
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(universe);
        when(findCategoryPort.findUniverseCategory(1L)).thenReturn(new UniverseCategory(1L, "category", "카테고리"));
        sut.updateDetail(universe.getId(), command);

        // then
        verify(updateUniversePort, times(1)).updateDetail(universe);
        assertThat(universe.getBasicInfo().getTitle()).isEqualTo("오르트구름");
        assertThat(universe.getBasicInfo().getDescription()).isEqualTo("오르트구름은 태양계 최외곽에 위치하고 있습니다.");
        assertThat(universe.getCategory().getEng()).isEqualTo("category");
        assertThat(universe.getBasicInfo().getPublicStatus()).isEqualTo(PublicStatus.PRIVATE);
        assertThat(universe.getSocialInfo().getHashtags()).contains("오르트구름", "태양계", "윤하", "별");

    }

    @Test
    @DisplayName("썸네일 수정 서비스")
    void updateDetailThumbnail() {
        // given
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(universe);
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_thumb.png", "test1235.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        sut.updateThumbnail(universe.getId(), thumbnail);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).updateThumbnail(universe);
    }

    @Test
    @DisplayName("썸뮤직 수정 서비스")
    void updateDetailThumbmusic() {
        // given
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_thumb.mp3", "audio/mpeg", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(universe);
        when(uploadPublicAudioUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        sut.updateThumbMusic(universe.getId(), thumbMusic);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).updateThumbMusic(universe);
    }

    @Test
    @DisplayName("내부이미지 수정 서비스")
    void updateDetailInnerImage() {
        // given
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(universe);
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_inner_image.png", "test1235.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        sut.updateInnerImage(universe.getId(), innerImage);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).updateInnerImage(universe);
    }
}