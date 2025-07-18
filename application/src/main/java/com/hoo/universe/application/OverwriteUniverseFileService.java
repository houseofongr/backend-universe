package com.hoo.universe.application;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.common.internal.api.UploadFileAPI;
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
    private final UploadFileAPI uploadFileAPI;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public OverwriteUniverseFileResult.Thumbmusic overwriteUniverseThumbmusic(UUID universeID, FileCommand thumbmusic) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);

        UUID newThumbmusicID = uploadFileAPI.uploadFile(UploadFileCommand.from(thumbmusic, universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel())).id();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(newThumbmusicID, null, null);

        handleUniverseEventPort.handleUniverseFileOverwriteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Thumbmusic(event.oldThumbmusicID(), event.newThumbmusicID());
    }

    @Override
    public OverwriteUniverseFileResult.Thumbnail overwriteUniverseThumbnail(UUID universeID, FileCommand thumbnail) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);

        UUID newThumbnailID = uploadFileAPI.uploadFile(UploadFileCommand.from(thumbnail, universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel())).id();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, newThumbnailID, null);

        handleUniverseEventPort.handleUniverseFileOverwriteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Thumbnail(event.oldThumbnailID(), event.newThumbnailID());
    }

    @Override
    public OverwriteUniverseFileResult.Background overwriteUniverseBackground(UUID universeID, FileCommand background) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);

        UUID newBackgroundID = uploadFileAPI.uploadFile(UploadFileCommand.from(background, universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel())).id();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, null, newBackgroundID);

        handleUniverseEventPort.handleUniverseFileOverwriteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Background(event.oldBackgroundID(), event.newBackgroundID());
    }
}
