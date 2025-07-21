package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.UpdatePieceMetadataCommand;
import com.hoo.universe.api.in.dto.UpdatePieceMetadataResult;
import com.hoo.universe.api.in.UpdatePieceMetadataUseCase;
import com.hoo.universe.api.out.HandlePieceEventPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.piece.PieceMetadataUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePieceMetadataService implements UpdatePieceMetadataUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandlePieceEventPort handlePieceEventPort;

    @Override
    public UpdatePieceMetadataResult updatePieceMetadata(UUID universeID, UUID pieceID, UpdatePieceMetadataCommand command) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Piece piece = universe.getPiece(new Piece.PieceID(pieceID));

        PieceMetadataUpdateEvent event = piece.updateMetadata(command.title(), command.description(), command.hidden());

        handlePieceEventPort.handlePieceMetadataUpdateEvent(event);

        return new UpdatePieceMetadataResult(
                piece.getCommonMetadata().getTitle(),
                piece.getCommonMetadata().getDescription(),
                piece.getPieceMetadata().isHidden(),
                piece.getCommonMetadata().getUpdatedTime().toEpochSecond()
        );
    }
}
