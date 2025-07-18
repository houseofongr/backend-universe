package com.hoo.universe.application.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.test.dto.UploadFileTestData.*;
import static org.mockito.Mockito.*;

class OverwriteSpaceFileServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleSpaceEventPort handleSpaceEventPort = mock();
    FileUploadAPI fileUploadAPI = mock();
    FileDeleteEventPublisher fileDeleteEventPublisher = mock();

    OverwriteSpaceFileService sut = new OverwriteSpaceFileService(loadUniversePort, handleSpaceEventPort, fileUploadAPI, fileDeleteEventPublisher);

    @Test
    @DisplayName("스페이스 파일 덮어쓰기 서비스")
    void overwriteSpaceFileService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UploadFileRequest background = defaultImageFileRequest();
        Universe universe = defaultUniverse();
        UUID spaceID = universe.getSpaces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        when(fileUploadAPI.uploadFile(background)).thenReturn(defaultImageFileResponse());
        sut.overwriteSpaceFile(universeID, spaceID, background);

        // then
        verify(handleSpaceEventPort, times(1)).handleSpaceFileOverwriteEvent(any());
        verify(fileDeleteEventPublisher, times(1)).publishDeleteFilesEvent((UUID) any());
    }
}