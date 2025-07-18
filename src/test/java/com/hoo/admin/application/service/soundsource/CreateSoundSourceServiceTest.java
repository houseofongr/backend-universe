package com.hoo.admin.application.service.soundsource;

import com.hoo.admin.application.port.in.soundsource.CreateSoundSourceMetadata;
import com.hoo.admin.application.port.in.soundsource.CreateSoundSourceResult;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.port.out.soundsource.CreateSoundSourcePort;
import com.hoo.admin.application.port.out.soundsource.SaveSoundSourcePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.common.application.service.MockEntityFactoryService;
import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPrivateAudioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static com.hoo.common.util.GsonUtil.gson;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateSoundSourceServiceTest {

    CreateSoundSourceService sut;
    FindItemPort findItemPort;
    CreateSoundSourcePort createSoundSourcePort;
    SaveSoundSourcePort saveSoundSourcePort;
    UploadPrivateAudioUseCase uploadPrivateAudioUseCase;

    @BeforeEach
    void init() {
        findItemPort = mock();
        createSoundSourcePort = mock();
        saveSoundSourcePort = mock();
        uploadPrivateAudioUseCase = mock();
        sut = new CreateSoundSourceService(findItemPort, createSoundSourcePort, saveSoundSourcePort, uploadPrivateAudioUseCase);
    }

    @Test
    @DisplayName("음원 생성 서비스 테스트")
    void testCreateSoundSource() throws Exception {
        // given

        //language=JSON
        String data = """
                {
                  "name" : "골골송",
                  "description" : "2025년 설이가 보내는 골골송",
                  "isActive" : true
                }
                """;
        Long itemId = 1L;
        CreateSoundSourceMetadata metadata = gson.fromJson(data, CreateSoundSourceMetadata.class);
        MockMultipartFile soundFile = new MockMultipartFile("soundFile", "golgolSong.mp3", "audio/mpeg", "golgolgolgolgolgolgolgol".getBytes());

        // when
        when(findItemPort.loadItem(itemId)).thenReturn(Optional.of(MockEntityFactoryService.getRectangleItem()));
        when(createSoundSourcePort.createSoundSource(any(), any(), any(), any(), any())).thenReturn(MockEntityFactoryService.getSoundSource());
        when(saveSoundSourcePort.saveSoundSource(any())).thenReturn(1L);
        when(uploadPrivateAudioUseCase.privateUpload(any(), any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(1L, 1L, "golgol.mp3", "test1234.mp3", "1234KB", Authority.ALL_PRIVATE_IMAGE_ACCESS))));
        CreateSoundSourceResult result = sut.createSoundSource(itemId, metadata, soundFile);

        // then
        assertThatThrownBy(() -> sut.createSoundSource(123L, metadata, soundFile)).hasMessage(AdminErrorCode.ITEM_NOT_FOUND.getMessage());
        assertThat(result.soundSourceId()).isEqualTo(1L);
    }
}