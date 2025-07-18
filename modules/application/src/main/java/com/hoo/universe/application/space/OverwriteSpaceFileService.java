package com.hoo.universe.application.space;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.dto.result.space.OverwriteSpaceFileResult;
import com.hoo.universe.api.in.space.OverwriteSpaceFileUseCase;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Space;
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
    public OverwriteSpaceFileResult overwriteSpaceFile(UUID universeID, UUID spaceID, FileCommand background) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Space space = universe.getSpace(new SpaceID(spaceID));
        UploadFileResult audio = uploadFileAPI.uploadFile(UploadFileCommand.from(background, universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel()));

        SpaceFileOverwriteEvent event = space.overwriteFile(audio.id());

        handleSpaceEventPort.handleSpaceFileOverwriteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.oldInnerImageID());

        return new OverwriteSpaceFileResult(audio.fileUrl());
    }
}
