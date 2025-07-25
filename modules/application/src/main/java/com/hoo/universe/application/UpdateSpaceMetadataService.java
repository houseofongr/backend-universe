package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.UpdateSpaceMetadataCommand;
import com.hoo.universe.api.in.dto.UpdateSpaceMetadataResult;
import com.hoo.universe.api.in.UpdateSpaceMetadataUseCase;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.UpdateSpaceStatusPort;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.event.SpaceMetadataUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSpaceMetadataService implements UpdateSpaceMetadataUseCase {

    private final LoadUniversePort loadUniversePort;
    private final UpdateSpaceStatusPort updateSpaceStatusPort;

    @Override
    public UpdateSpaceMetadataResult updateSpaceMetadata(UUID universeID, UUID spaceID, UpdateSpaceMetadataCommand command) {
        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Space space = universe.getSpace(new SpaceID(spaceID));

        SpaceMetadataUpdateEvent event = space.updateMetadata(command.title(), command.description(), command.hidden());

        updateSpaceStatusPort.updateSpaceMetadata(event);

        return new UpdateSpaceMetadataResult(
                space.getCommonMetadata().getTitle(),
                space.getCommonMetadata().getDescription(),
                space.getSpaceMetadata().isHidden(),
                space.getCommonMetadata().getUpdatedTime().toEpochSecond()
        );
    }
}
