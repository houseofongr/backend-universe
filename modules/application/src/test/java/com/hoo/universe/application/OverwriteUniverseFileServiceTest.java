package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.in.dto.OverwriteUniverseFileResult;
import com.hoo.universe.api.out.HandleUniverseEventPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static com.hoo.universe.test.dto.FileTestData.*;
import static org.mockito.Mockito.*;

class OverwriteUniverseFileServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleUniverseEventPort handleUniverseEventPort = mock();
    UploadFileAPI uploadFileAPI = mock();
    DeleteFileEventPublisher deleteFileEventPublisher = mock();

    OverwriteUniverseFileService sut = new OverwriteUniverseFileService(loadUniversePort, handleUniverseEventPort, uploadFileAPI, deleteFileEventPublisher);

    @Test
    @DisplayName("파일 덮어쓰기 서비스")
    void overwriteFileService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();

        FileCommand thumbmusic = defaultAudioFileCommand();
        FileCommand thumbnail = defaultImageFileCommand();
        FileCommand background = defaultImageFileCommand();

        Universe universe = defaultUniverseOnly().build();

        // when
        UploadFileResult thumbmusicFileResponse = defaultFileResponse();
        UploadFileResult thumbnailFileResponse = defaultFileResponse();
        UploadFileResult backgroundFileResponse = defaultFileResponse();
        when(loadUniversePort.loadUniverseOnly(universeID)).thenReturn(universe);
        when(uploadFileAPI.uploadFile(any())).thenReturn(thumbmusicFileResponse);
        when(uploadFileAPI.uploadFile(any())).thenReturn(thumbnailFileResponse);
        when(uploadFileAPI.uploadFile(any())).thenReturn(backgroundFileResponse);

        OverwriteUniverseFileResult.Thumbmusic result = sut.overwriteUniverseThumbmusic(universeID, thumbmusic);
        verify(deleteFileEventPublisher, times(1)).publishDeleteFilesEvent(anyList());
        verify(handleUniverseEventPort, times(1)).handleUniverseFileOverwriteEvent(any());
        result.newThumbmusicID().equals(thumbmusicFileResponse.id());

        OverwriteUniverseFileResult.Thumbnail result2 = sut.overwriteUniverseThumbnail(universeID, thumbnail);
        verify(deleteFileEventPublisher, times(2)).publishDeleteFilesEvent(anyList());
        verify(handleUniverseEventPort, times(2)).handleUniverseFileOverwriteEvent(any());
        result2.newThumbnailID().equals(thumbnailFileResponse.id());

        OverwriteUniverseFileResult.Background result3 = sut.overwriteUniverseBackground(universeID, background);
        verify(deleteFileEventPublisher, times(3)).publishDeleteFilesEvent(anyList());
        verify(handleUniverseEventPort, times(3)).handleUniverseFileOverwriteEvent(any());
        result3.newBackgroundID().equals(backgroundFileResponse.id());

    }

}