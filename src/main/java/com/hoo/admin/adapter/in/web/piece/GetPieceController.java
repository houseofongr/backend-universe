package com.hoo.admin.adapter.in.web.piece;

import com.hoo.admin.application.port.in.piece.SearchPieceCommand;
import com.hoo.admin.application.port.in.piece.SearchPieceResult;
import com.hoo.admin.application.port.in.piece.SearchPieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPieceController {

    private final SearchPieceUseCase useCase;

    @GetMapping("/admin/pieces/{pieceId}")
    ResponseEntity<SearchPieceResult> search(
            Pageable pageable,
            @PathVariable Long pieceId,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Boolean isAsc
    ) {
        return ResponseEntity.ok(useCase.search(new SearchPieceCommand(pageable, pieceId, sortType, isAsc)));
    }
}
