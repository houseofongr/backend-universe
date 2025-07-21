package com.hoo.universe.adapter.in.web.piece;

import com.hoo.universe.api.in.dto.UpdatePieceMetadataCommand;
import com.hoo.universe.api.in.dto.UpdatePieceMetadataResult;
import com.hoo.universe.api.in.UpdatePieceMetadataUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdatePieceMetadataController {

    private final UpdatePieceMetadataUseCase useCase;

    @PatchMapping("/universes/{universeID}/pieces/{pieceID}")
    ResponseEntity<UpdatePieceMetadataResult> update(
            @PathVariable UUID universeID,
            @PathVariable UUID pieceID,
            @RequestBody UpdatePieceMetadataCommand command) {

        return ResponseEntity.ok(useCase.updatePieceMetadata(universeID, pieceID, command));
    }
}
