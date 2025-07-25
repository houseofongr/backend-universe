package com.hoo.universe.application;

import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.in.dto.DeleteSpaceResult;
import com.hoo.universe.api.in.DeleteSpaceUseCase;
import com.hoo.universe.api.out.DeleteEntityPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.SpaceDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteSpaceService implements DeleteSpaceUseCase {

    private final LoadUniversePort loadUniversePort;
    private final DeleteEntityPort deleteEntityPort;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public DeleteSpaceResult deleteSpace(UUID universeID, UUID spaceID) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Space space = universe.getSpace(new SpaceID(spaceID));

        SpaceDeleteEvent event = space.delete();

        deleteEntityPort.deleteSpace(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.deleteFileIDs());

        return new DeleteSpaceResult(
                event.deleteSpaceIDs().stream().map(SpaceID::uuid).toList(),
                event.deletePieceIDs().stream().map(PieceID::uuid).toList(),
                event.deleteSoundIDs().stream().map(SoundID::uuid).toList(),
                event.deleteFileIDs().size()
        );
    }
}
