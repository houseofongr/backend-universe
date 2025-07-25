package com.hoo.universe.application;

import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.in.dto.DeleteUniverseResult;
import com.hoo.universe.api.in.DeleteUniverseUseCase;
import com.hoo.universe.api.out.DeleteEntityPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.UniverseDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteUniverseService implements DeleteUniverseUseCase {

    private final LoadUniversePort loadUniversePort;
    private final DeleteEntityPort deleteEntityPort;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public DeleteUniverseResult deleteUniverse(UUID universeID) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);

        UniverseDeleteEvent event = universe.delete();

        deleteEntityPort.deleteUniverse(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.deleteFileIDs());

        return new DeleteUniverseResult(
                universeID,
                event.deleteSpaceIDs().stream().map(SpaceID::uuid).toList(),
                event.deletePieceIDs().stream().map(PieceID::uuid).toList(),
                event.deleteSoundIDs().stream().map(SoundID::uuid).toList(),
                event.deleteFileIDs().size()
        );
    }
}
