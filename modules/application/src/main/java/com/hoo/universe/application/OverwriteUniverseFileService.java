package com.hoo.universe.application;

import com.hoo.common.enums.Domain;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.in.OverwriteUniverseFileUseCase;
import com.hoo.universe.api.in.dto.OverwriteUniverseFileResult;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.UpdateUniverseStatusPort;
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
    private final UpdateUniverseStatusPort updateUniverseStatusPort;
    private final UploadFileAPI uploadFileAPI;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public OverwriteUniverseFileResult.Thumbmusic overwriteUniverseThumbmusic(UUID universeID, UploadFileCommand.FileSource thumbmusic) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);
        UUID newThumbmusicID = getFileID(thumbmusic, universe);

        UniverseFileOverwriteEvent event = universe.overwriteFiles(newThumbmusicID, null, null);

        updateUniverseStatusPort.updateUniverseFileOverwrite(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Thumbmusic(event.oldThumbmusicID(), event.newThumbmusicID());
    }

    @Override
    public OverwriteUniverseFileResult.Thumbnail overwriteUniverseThumbnail(UUID universeID, UploadFileCommand.FileSource thumbnail) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);
        UUID newThumbnailID = getFileID(thumbnail, universe);

        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, newThumbnailID, null);

        updateUniverseStatusPort.updateUniverseFileOverwrite(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Thumbnail(event.oldThumbnailID(), event.newThumbnailID());
    }

    @Override
    public OverwriteUniverseFileResult.Background overwriteUniverseBackground(UUID universeID, UploadFileCommand.FileSource background) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);
        UUID newBackgroundID = getFileID(background, universe);

        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, null, newBackgroundID);

        updateUniverseStatusPort.updateUniverseFileOverwrite(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.getOldFileIDs());

        return new OverwriteUniverseFileResult.Background(event.oldBackgroundID(), event.newBackgroundID());
    }

    private UUID getFileID(UploadFileCommand.FileSource background, Universe universe) {
        UploadFileCommand command = UploadFileCommand.from(background, Domain.UNIVERSE.getName(), universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel());
        return uploadFileAPI.uploadFile(command).id();
    }
}
