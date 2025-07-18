package com.hoo.universe.application.space;

import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.api.dto.UploadFileResponse;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.common.internal.api.FileUploadAPI;
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
    private final FileUploadAPI fileUploadAPI;
    private final FileDeleteEventPublisher fileDeleteEventPublisher;

    @Override
    public OverwriteSpaceFileResult overwriteSpaceFile(UUID universeID, UUID spaceID, UploadFileRequest background) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Space space = universe.getSpace(new SpaceID(spaceID));
        UploadFileResponse response = fileUploadAPI.uploadFile(background);

        SpaceFileOverwriteEvent event = space.overwriteFile(response.id());

        handleSpaceEventPort.handleSpaceFileOverwriteEvent(event);
        fileDeleteEventPublisher.publishDeleteFilesEvent(event.oldInnerImageID());

        return new OverwriteSpaceFileResult(
                event.oldInnerImageID(),
                event.newInnerImageID()
        );
    }
}
