package com.hoo.universe.application.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.test.dto.UploadFileTestData.*;
import static org.mockito.Mockito.*;

class OverwriteSoundFileServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleSoundEventPort handleSoundEventPort = mock();
    FileUploadAPI fileUploadAPI = mock();
    FileDeleteEventPublisher fileDeleteEventPublisher = mock();

    OverwriteSoundFileService sut = new OverwriteSoundFileService(loadUniversePort, handleSoundEventPort, fileUploadAPI, fileDeleteEventPublisher);

    @Test
    @DisplayName("사운드 파일 덮어쓰기 서비스")
    void overwriteSoundAudioService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UploadFileRequest audio = defaultAudioFileRequest();
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();
        UUID soundID = universe.getPieces().getFirst().getSounds().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseWithAllEntity(universeID)).thenReturn(universe);
        when(fileUploadAPI.uploadFile(audio)).thenReturn(defaultAudioFileResponse());
        sut.overwriteSoundAudio(universeID, pieceID, soundID, audio);

        // then
        verify(handleSoundEventPort, times(1)).handleSoundFileOverwriteEvent(any());
    }

}