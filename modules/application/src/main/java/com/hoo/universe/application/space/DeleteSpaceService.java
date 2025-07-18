package com.hoo.universe.application.space;

import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.dto.result.space.DeleteSpaceResult;
import com.hoo.universe.api.in.space.DeleteSpaceUseCase;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.space.SpaceDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteSpaceService implements DeleteSpaceUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandleSpaceEventPort handleSpaceEventPort;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public DeleteSpaceResult deleteSpace(UUID universeID, UUID spaceID) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Space space = universe.getSpace(new SpaceID(spaceID));

        SpaceDeleteEvent event = space.delete();

        handleSpaceEventPort.handleSpaceDeleteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.deleteFileIDs());

        return new DeleteSpaceResult(
                event.deleteSpaceIDs().stream().map(SpaceID::uuid).toList(),
                event.deletePieceIDs().stream().map(PieceID::uuid).toList(),
                event.deleteSoundIDs().stream().map(SoundID::uuid).toList(),
                event.deleteFileIDs().size()
        );
    }
}
