package com.hoo.universe.application;

import com.hoo.common.enums.Domain;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.api.file.dto.UploadFileResult;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.in.OverwriteSpaceFileUseCase;
import com.hoo.universe.api.in.dto.OverwriteSpaceFileResult;
import com.hoo.universe.api.out.HandleSpaceEventPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.space.SpaceFileOverwriteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OverwriteSpaceFileService implements OverwriteSpaceFileUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandleSpaceEventPort handleSpaceEventPort;
    private final UploadFileAPI uploadFileAPI;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public OverwriteSpaceFileResult overwriteSpaceFile(UUID universeID, UUID spaceID, UploadFileCommand.FileSource background) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Space space = universe.getSpace(new SpaceID(spaceID));
        UploadFileResult audio = uploadFileAPI.uploadFile(new UploadFileCommand(
                background,
                new UploadFileCommand.Metadata(Domain.UNIVERSE.getName(), universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel()))
        );

        SpaceFileOverwriteEvent event = space.overwriteFile(audio.id());

        handleSpaceEventPort.handleSpaceFileOverwriteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.oldInnerImageID());

        return new OverwriteSpaceFileResult(audio.fileUrl());
    }
}
