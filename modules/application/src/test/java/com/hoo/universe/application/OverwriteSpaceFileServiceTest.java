package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.UpdateSpaceStatusPort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.test.dto.FileTestData.*;
import static org.mockito.Mockito.*;

class OverwriteSpaceFileServiceTest {

    LoadUniversePort loadUniversePort = mock();
    UpdateSpaceStatusPort updateSpaceStatusPort = mock();
    UploadFileAPI uploadFileAPI = mock();
    DeleteFileEventPublisher deleteFileEventPublisher = mock();

    OverwriteSpaceFileService sut = new OverwriteSpaceFileService(loadUniversePort, updateSpaceStatusPort, uploadFileAPI, deleteFileEventPublisher);

    @Test
    @DisplayName("스페이스 파일 덮어쓰기 서비스")
    void overwriteSpaceFileService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UploadFileCommand.FileSource background = defaultImageFileSource();
        Universe universe = defaultUniverse();
        UUID spaceID = universe.getSpaces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());
        sut.overwriteSpaceFile(universeID, spaceID, background);

        // then
        verify(updateSpaceStatusPort, times(1)).updateSpaceFileOverwrite(any());
        verify(deleteFileEventPublisher, times(1)).publishDeleteFilesEvent((UUID) any());
    }
}