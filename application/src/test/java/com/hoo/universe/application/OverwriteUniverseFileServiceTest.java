package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.api.dto.UploadFileResponse;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.universe.api.dto.result.OverwriteUniverseFileResult;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static com.hoo.universe.test.dto.UploadFileTestData.*;
import static org.mockito.Mockito.*;

class OverwriteUniverseFileServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleUniverseEventPort handleUniverseEventPort = mock();
    FileUploadAPI fileUploadAPI = mock();
    FileDeleteEventPublisher fileDeleteEventPublisher = mock();

    OverwriteUniverseFileService sut = new OverwriteUniverseFileService(loadUniversePort, handleUniverseEventPort, fileUploadAPI, fileDeleteEventPublisher);

    @Test
    @DisplayName("파일 덮어쓰기 서비스")
    void overwriteFileService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();

        UploadFileRequest thumbmusic = defaultAudioFileRequest();
        UploadFileRequest thumbnail = defaultImageFileRequest();
        UploadFileRequest background = defaultImageFileRequest();

        Universe universe = defaultUniverseOnly().build();

        // when
        UploadFileResponse thumbmusicFileResponse = defaultAudioFileResponse();
        UploadFileResponse thumbnailFileResponse = defaultImageFileResponse();
        UploadFileResponse backgroundFileResponse = defaultImageFileResponse();
        when(loadUniversePort.loadUniverseOnly(universeID)).thenReturn(universe);
        when(fileUploadAPI.uploadFile(thumbmusic)).thenReturn(thumbmusicFileResponse);
        when(fileUploadAPI.uploadFile(thumbnail)).thenReturn(thumbnailFileResponse);
        when(fileUploadAPI.uploadFile(background)).thenReturn(backgroundFileResponse);

        OverwriteUniverseFileResult.Thumbmusic result = sut.overwriteUniverseThumbmusic(universeID, thumbmusic);
        verify(fileDeleteEventPublisher, times(1)).publishDeleteFilesEvent(anyList());
        verify(handleUniverseEventPort, times(1)).handleUniverseFileOverwriteEvent(any());
        result.newThumbmusicID().equals(thumbmusicFileResponse.id());

        OverwriteUniverseFileResult.Thumbnail result2 = sut.overwriteUniverseThumbnail(universeID, thumbnail);
        verify(fileDeleteEventPublisher, times(2)).publishDeleteFilesEvent(anyList());
        verify(handleUniverseEventPort, times(2)).handleUniverseFileOverwriteEvent(any());
        result2.newThumbnailID().equals(thumbnailFileResponse.id());

        OverwriteUniverseFileResult.Background result3 = sut.overwriteUniverseBackground(universeID, background);
        verify(fileDeleteEventPublisher, times(3)).publishDeleteFilesEvent(anyList());
        verify(handleUniverseEventPort, times(3)).handleUniverseFileOverwriteEvent(any());
        result3.newBackgroundID().equals(backgroundFileResponse.id());

    }

}