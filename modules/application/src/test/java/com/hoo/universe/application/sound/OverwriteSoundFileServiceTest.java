package com.hoo.universe.application.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.application.OverwriteSoundFileService;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.test.dto.FileTestData.*;
import static org.mockito.Mockito.*;

class OverwriteSoundFileServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleSoundEventPort handleSoundEventPort = mock();
    UploadFileAPI uploadFileAPI = mock();
    DeleteFileEventPublisher deleteFileEventPublisher = mock();

    OverwriteSoundFileService sut = new OverwriteSoundFileService(loadUniversePort, handleSoundEventPort, uploadFileAPI, deleteFileEventPublisher);

    @Test
    @DisplayName("사운드 파일 덮어쓰기 서비스")
    void overwriteSoundAudioService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        FileCommand audio = defaultAudioFileCommand();
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();
        UUID soundID = universe.getPieces().getFirst().getSounds().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseWithAllEntity(universeID)).thenReturn(universe);
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());
        sut.overwriteSoundAudio(universeID, pieceID, soundID, audio);

        // then
        verify(handleSoundEventPort, times(1)).handleSoundFileOverwriteEvent(any());
    }

}