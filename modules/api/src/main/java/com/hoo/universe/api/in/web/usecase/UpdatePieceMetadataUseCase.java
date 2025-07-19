package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.UpdatePieceMetadataCommand;
import com.hoo.universe.api.in.web.dto.result.UpdatePieceMetadataResult;

import java.util.UUID;

public interface UpdatePieceMetadataUseCase {
    UpdatePieceMetadataResult updatePieceMetadata(UUID universeID, UUID spaceID, UpdatePieceMetadataCommand command);
}
