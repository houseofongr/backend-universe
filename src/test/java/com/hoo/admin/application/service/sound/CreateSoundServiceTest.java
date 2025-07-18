package com.hoo.admin.application.service.sound;

import com.hoo.admin.application.port.in.sound.CreateSoundCommand;
import com.hoo.admin.application.port.in.sound.CreateSoundResult;
import com.hoo.admin.application.port.out.sound.CreateSoundPort;
import com.hoo.admin.application.port.out.sound.SaveSoundPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicAudioUseCase;
import com.hoo.file.domain.FileSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateSoundServiceTest {

    UploadPublicAudioUseCase uploadPublicAudioUseCase = mock();
    CreateSoundPort createSoundPort = mock();
    SaveSoundPort saveSoundPort = mock();
    CreateSoundService sut = new CreateSoundService(uploadPublicAudioUseCase, createSoundPort, saveSoundPort);

    @Test
    @DisplayName("요청 파라미터 테스트")
    void testRequestParameter() {
        Long goodPieceId = 1L;
        String goodTitle = "소리";
        String goodDescription = "사운드는 소리입니다.";
        Long nullPieceId = null;
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new CreateSoundCommand(nullPieceId, goodTitle, goodDescription, true, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSoundCommand(goodPieceId, emptyTitle, goodDescription, true, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSoundCommand(goodPieceId, blankTitle, goodDescription, true, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSoundCommand(goodPieceId, length100, goodDescription, true, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSoundCommand(goodPieceId, goodTitle, length5000, true, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSoundCommand(goodPieceId, goodTitle, goodDescription, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        byte[] content = new byte[100 * 1024 * 1024 + 1];
        MockMultipartFile sound = new MockMultipartFile("audio", "audio.png", "audio/mpeg", "audio file".getBytes());
        MockMultipartFile over100MB = new MockMultipartFile("audio", "audio.png", "audio/mpeg", content);
        CreateSoundCommand happyCommand = new CreateSoundCommand(goodPieceId, goodTitle, goodDescription, false, null);

        assertThatThrownBy(() -> CreateSoundCommand.withAudioFile(happyCommand, over100MB)).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());

        CreateSoundCommand happyFullCommand = CreateSoundCommand.withAudioFile(happyCommand, sound);
        assertThat(happyFullCommand.pieceId()).isEqualTo(goodPieceId);
        assertThat(happyFullCommand.title()).isEqualTo(goodTitle);
        assertThat(happyFullCommand.description()).isEqualTo(goodDescription);
        assertThat(happyFullCommand.audioFile()).isEqualTo(sound);
    }

    @Test
    @DisplayName("사운드 생성 서비스")
    void testCreateSoundService() {
        // given
        CreateSoundCommand happyCommand = new CreateSoundCommand(1L, "소리", "사운드는 소리입니다.", false, null);
        CreateSoundCommand happyFullCommand = CreateSoundCommand.withAudioFile(happyCommand, new MockMultipartFile("audio", "audio.png", "audio/mpeg", "audio file".getBytes()));
        Sound sound = Sound.create(123L, 345L, 1L, "소리", "사운드는 소리입니다.", false);

        // when
        when(createSoundPort.createSound(anyLong(), any())).thenReturn(sound);
        when(uploadPublicAudioUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(345L, null, "audio.png", "test1234.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        when(saveSoundPort.save(sound)).thenReturn(123L);
        CreateSoundResult result = sut.create(happyFullCommand);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 사운드가 생성되었습니다.");
        assertThat(result.soundId()).isEqualTo(123L);
        assertThat(result.pieceId()).isEqualTo(1L);
        assertThat(result.audioFileId()).isEqualTo(345L);
        assertThat(result.title()).isEqualTo("소리");
        assertThat(result.description()).isEqualTo("사운드는 소리입니다.");
    }
}