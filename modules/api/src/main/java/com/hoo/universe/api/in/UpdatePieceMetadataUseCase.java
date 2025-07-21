package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.UpdatePieceMetadataCommand;
import com.hoo.universe.api.in.dto.UpdatePieceMetadataResult;

import java.util.UUID;

public interface UpdatePieceMetadataUseCase {
    UpdatePieceMetadataResult updatePieceMetadata(UUID universeID, UUID spaceID, UpdatePieceMetadataCommand command);
}
