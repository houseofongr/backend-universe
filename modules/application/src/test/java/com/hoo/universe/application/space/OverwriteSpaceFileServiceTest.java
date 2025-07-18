package com.hoo.universe.application.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.test.dto.FileTestData.*;
import static org.mockito.Mockito.*;

class OverwriteSpaceFileServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleSpaceEventPort handleSpaceEventPort = mock();
    UploadFileAPI uploadFileAPI = mock();
    DeleteFileEventPublisher deleteFileEventPublisher = mock();

    OverwriteSpaceFileService sut = new OverwriteSpaceFileService(loadUniversePort, handleSpaceEventPort, uploadFileAPI, deleteFileEventPublisher);

    @Test
    @DisplayName("스페이스 파일 덮어쓰기 서비스")
    void overwriteSpaceFileService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        FileCommand background = defaultImageFileCommand();
        Universe universe = defaultUniverse();
        UUID spaceID = universe.getSpaces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());
        sut.overwriteSpaceFile(universeID, spaceID, background);

        // then
        verify(handleSpaceEventPort, times(1)).handleSpaceFileOverwriteEvent(any());
        verify(deleteFileEventPublisher, times(1)).publishDeleteFilesEvent((UUID) any());
    }
}