package com.hoo.admin.adapter.in.web.piece;

import com.hoo.admin.application.port.in.piece.UpdatePieceCommand;
import com.hoo.admin.application.port.in.piece.UpdatePieceResult;
import com.hoo.admin.application.port.in.piece.UpdatePieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchPieceController {

    private final UpdatePieceUseCase useCase;

    @PatchMapping("/admin/pieces/{pieceId}")
    ResponseEntity<UpdatePieceResult.Detail> update(
            @PathVariable Long pieceId,
            @RequestBody UpdatePieceCommand.Detail command) {

        return ResponseEntity.ok(useCase.updateDetail(pieceId, command));
    }

    @PatchMapping("/admin/pieces/position/{pieceId}")
    ResponseEntity<UpdatePieceResult.Position> updatePosition(
            @PathVariable Long pieceId,
            @RequestBody UpdatePieceCommand.Position command) {

        return ResponseEntity.ok(useCase.updatePosition(pieceId, command));
    }
}
