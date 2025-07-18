package com.hoo.universe.application;

import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.universe.api.dto.result.OverwriteUniverseFileResult;
import com.hoo.universe.api.in.OverwriteUniverseFileUseCase;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OverwriteUniverseFileService implements OverwriteUniverseFileUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandleUniverseEventPort handleUniverseEventPort;
    private final FileUploadAPI fileUploadAPI;
    private final FileDeleteEventPublisher fileDeleteEventPublisher;

    @Override
    public OverwriteUniverseFileResult.Thumbmusic overwriteUniverseThumbmusic(UUID universeID, UploadFileRequest thumbmusic) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);

        UUID newThumbmusicID = fileUploadAPI.uploadFile(thumbmusic).id();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(newThumbmusicID, null, null);

        handleUniverseEventPort.handleUniverseFileOverwriteEvent(event);
        fileDeleteEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Thumbmusic(event.oldThumbmusicID(), event.newThumbmusicID());
    }

    @Override
    public OverwriteUniverseFileResult.Thumbnail overwriteUniverseThumbnail(UUID universeID, UploadFileRequest thumbnail) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);

        UUID newThumbnailID = fileUploadAPI.uploadFile(thumbnail).id();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, newThumbnailID, null);

        handleUniverseEventPort.handleUniverseFileOverwriteEvent(event);
        fileDeleteEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Thumbnail(event.oldThumbnailID(), event.newThumbnailID());
    }

    @Override
    public OverwriteUniverseFileResult.Background overwriteUniverseBackground(UUID universeID, UploadFileRequest background) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);

        UUID newBackgroundID = fileUploadAPI.uploadFile(background).id();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, null, newBackgroundID);

        handleUniverseEventPort.handleUniverseFileOverwriteEvent(event);
        fileDeleteEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Background(event.oldBackgroundID(), event.newBackgroundID());
    }
}
