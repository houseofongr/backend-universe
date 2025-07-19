package com.hoo.universe.application;

import com.hoo.universe.api.in.web.dto.command.UpdateSpaceMetadataCommand;
import com.hoo.universe.api.in.web.dto.result.UpdateSpaceMetadataResult;
import com.hoo.universe.api.in.web.usecase.UpdateSpaceMetadataUseCase;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.event.space.SpaceMetadataUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSpaceMetadataService implements UpdateSpaceMetadataUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandleSpaceEventPort handleSpaceEventPort;

    @Override
    public UpdateSpaceMetadataResult updateSpaceMetadata(UUID universeID, UUID spaceID, UpdateSpaceMetadataCommand command) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Space space = universe.getSpace(new SpaceID(spaceID));

        SpaceMetadataUpdateEvent event = space.updateMetadata(command.title(), command.description(), command.hidden());

        handleSpaceEventPort.handleSpaceMetadataUpdateEvent(event);

        return new UpdateSpaceMetadataResult(
                space.getCommonMetadata().getTitle(),
                space.getCommonMetadata().getDescription(),
                space.getSpaceMetadata().isHidden(),
                space.getCommonMetadata().getUpdatedTime().toEpochSecond()
        );
    }
}
