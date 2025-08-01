package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.universe.api.in.dto.CreateSoundCommand;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.SaveEntityPort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.test.dto.FileTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateSoundServiceTest {

    IssueIDPort issueIDPort = mock();
    LoadUniversePort loadUniversePort = mock();
    SaveEntityPort saveEntityPort = mock();
    UploadFileAPI uploadFileAPI = mock();

    CreateSoundService sut = new CreateSoundService(issueIDPort, loadUniversePort, saveEntityPort, uploadFileAPI);

    @Test
    @DisplayName("사운드 생성 서비스")
    void createSoundService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        CreateSoundCommand command = new CreateSoundCommand(new CreateSoundCommand.Metadata("소리", "사운드는 소리입니다.", false), defaultAudioFileSource());
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());
        sut.createNewSound(universeID, pieceID, command);

        // then
        verify(issueIDPort, times(1)).issueNewID();
        verify(loadUniversePort, times(1)).loadUniverseExceptSounds(any());
        verify(saveEntityPort, times(1)).saveSound(any());
    }

}