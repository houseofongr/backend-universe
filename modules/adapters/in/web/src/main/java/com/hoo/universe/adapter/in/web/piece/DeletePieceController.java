package com.hoo.universe.adapter.in.web.piece;

import com.hoo.universe.api.in.web.dto.result.DeletePieceResult;
import com.hoo.universe.api.in.web.usecase.DeletePieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeletePieceController {

    private final DeletePieceUseCase useCase;

    @DeleteMapping("/universes/{universeID}/pieces/{pieceID}")
    ResponseEntity<DeletePieceResult> delete(
            @PathVariable UUID universeID,
            @PathVariable UUID pieceID
    ) {
        return ResponseEntity.ok(useCase.deletePiece(universeID, pieceID));
    }
}
