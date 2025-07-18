package com.hoo.universe.api.in.piece;

import com.hoo.universe.api.dto.command.piece.UpdatePieceMetadataCommand;
import com.hoo.universe.api.dto.result.piece.UpdatePieceMetadataResult;

import java.util.UUID;

public interface UpdatePieceMetadataUseCase {
    UpdatePieceMetadataResult updatePieceMetadata(UUID universeID, UUID spaceID, UpdatePieceMetadataCommand command);
}
