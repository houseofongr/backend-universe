package com.hoo.universe.application.piece;

import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.dto.result.piece.DeletePieceResult;
import com.hoo.universe.api.in.piece.DeletePieceUseCase;
import com.hoo.universe.api.out.persistence.HandlePieceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.piece.PieceDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePieceService implements DeletePieceUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandlePieceEventPort handlePieceEventPort;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public DeletePieceResult deletePiece(UUID universeID, UUID pieceID) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Piece piece = universe.getPiece(new PieceID(pieceID));

        PieceDeleteEvent event = piece.delete();

        handlePieceEventPort.handlePieceDeleteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.deleteFileIDs());

        return new DeletePieceResult(
                event.deletePieceID().uuid(),
                event.deleteSoundIDs().stream().map(Sound.SoundID::uuid).toList(),
                event.deleteFileIDs().size()
        );
    }
}
