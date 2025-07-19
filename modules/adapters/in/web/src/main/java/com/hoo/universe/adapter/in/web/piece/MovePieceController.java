package com.hoo.universe.adapter.in.web.piece;

import com.hoo.universe.api.in.web.dto.command.MovePieceWithTwoPointCommand;
import com.hoo.universe.api.in.web.dto.result.MovePieceWithTwoPointResult;
import com.hoo.universe.api.in.web.usecase.MovePieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MovePieceController {

    private final MovePieceUseCase useCase;

    @PatchMapping("/universes/{universeID}/pieces/{pieceID}/move")
    ResponseEntity<MovePieceWithTwoPointResult> move(
            @PathVariable UUID universeID,
            @PathVariable UUID pieceID,
            @RequestBody MovePieceWithTwoPointCommand command
            ) {

        return ResponseEntity.ok(useCase.movePieceWithTwoPoint(universeID, pieceID, command));
    }
}
