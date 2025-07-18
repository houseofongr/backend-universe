package com.hoo.admin.adapter.in.web.piece;

import com.hoo.admin.application.port.in.piece.DeletePieceResult;
import com.hoo.admin.application.port.in.piece.DeletePieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeletePieceController {

    private final DeletePieceUseCase useCase;

    @DeleteMapping("/admin/pieces/{pieceId}")
    ResponseEntity<DeletePieceResult> delete(@PathVariable Long pieceId) {
        return ResponseEntity.ok(useCase.delete(pieceId));
    }
}
