package com.hoo.admin.application.service.sound;

import com.hoo.admin.application.port.in.sound.UpdateSoundCommand;
import com.hoo.admin.application.port.in.sound.UpdateSoundResult;
import com.hoo.admin.application.port.out.sound.FindSoundPort;
import com.hoo.admin.application.port.out.sound.UpdateSoundPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicAudioUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateSoundServiceTest {

    FindSoundPort findSoundPort = mock();
    UpdateSoundPort updateSoundPort = mock();
    UploadPublicAudioUseCase uploadPublicAudioUseCase = mock();
    DeleteFileUseCase deleteFileUseCase = mock();
    UpdateSoundService sut = new UpdateSoundService(findSoundPort, updateSoundPort, uploadPublicAudioUseCase, deleteFileUseCase);

    @Test
    @DisplayName("요청 파라미터 테스트")
    void testRequestParameter() {
        String goodTitle = "소리";
        String goodDescription = "사운드는 소리입니다.";
        Boolean hidden = true;
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new UpdateSoundCommand(emptyTitle, goodDescription, hidden)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSoundCommand(blankTitle, goodDescription, hidden)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSoundCommand(length100, goodDescription, hidden)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSoundCommand(goodTitle, length5000, hidden)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        new UpdateSoundCommand(goodTitle, null, null);
        new UpdateSoundCommand(null, goodDescription, null);
        new UpdateSoundCommand(null, null, true);
    }

    @Test
    @DisplayName("사운드 상세정보 업데이트 서비스")
    void testUpdateSoundDetailService() {
        // given
        Long id = 1L;
        UpdateSoundCommand command = new UpdateSoundCommand("수정", "수정할 내용", true);
        Sound sound = Sound.create(123L, 345L, 1L, "소리", "사운드는 소리입니다.", false);

        // when
        when(findSoundPort.find(id)).thenReturn(sound);
        UpdateSoundResult.Detail result = sut.updateDetail(id, command);

        // then
        verify(updateSoundPort, times(1)).update(sound);
        assertThat(result.message()).matches("\\[#\\d+]번 사운드의 상세정보가 수정되었습니다.");
        assertThat(result.title()).isEqualTo("수정");
        assertThat(result.description()).isEqualTo("수정할 내용");
        assertThat(result.hidden()).isTrue();
    }

    @Test
    @DisplayName("사운드 오디오 업데이트 서비스")
    void testUpdateSoundAudioService() {
        // given
        Long id = 1L;
        byte[] over100MB = new byte[100 * 1024 * 1024 + 1];
        MockMultipartFile audio = new MockMultipartFile("audio", "new_audio.mp3", "audio/mpeg", "audio file".getBytes());
        MockMultipartFile audioOver100MB = new MockMultipartFile("audio", "new_audio2.mp3", "audio/mpeg", over100MB);
        Sound sound = Sound.loadWithoutRelation(1L, 1L,"소리", "사운드는 소리입니다.", false, ZonedDateTime.now(), ZonedDateTime.now());

        // when
        when(findSoundPort.find(id)).thenReturn(sound);
        when(uploadPublicAudioUseCase.publicUpload(audio)).thenReturn(new UploadFileResult.FileInfo(2L, null, "new_audio.mp3", "test1234.mp3", "123KB", Authority.PUBLIC_FILE_ACCESS));
        UpdateSoundResult.Audio result = sut.updateAudio(id, audio);

        // then
        assertThatThrownBy(() -> sut.updateAudio(id, null)).hasMessage(AdminErrorCode.SOUND_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateAudio(id, audioOver100MB)).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());

        verify(updateSoundPort, times(1)).update(sound);
        verify(deleteFileUseCase, times(1)).deleteFile(1L);
        assertThat(result.message()).matches("\\[#\\d+]번 사운드의 오디오가 수정되었습니다.");
        assertThat(result.deletedAudioId()).isEqualTo(1L);
        assertThat(result.newAudioId()).isEqualTo(2L);
    }
}