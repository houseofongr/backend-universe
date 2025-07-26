package com.hoo.universe.adapter.in.web.piece;

import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.in.web.WebMapper;
import com.hoo.universe.api.in.dto.OpenPieceResult;
import com.hoo.universe.api.in.OpenPieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OpenPieceController {

    private final OpenPieceUseCase useCase;
    private final WebMapper webMapper;

    @GetMapping("/universes/pieces/{pieceID}")
    ResponseEntity<OpenPieceResult> search(
            Pageable pageable,
            @PathVariable UUID pieceID,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Boolean isAsc
    ) {

        PageRequest pageRequest = webMapper.mapToPageable(pageable, sortType, isAsc);
        return ResponseEntity.ok(useCase.openPieceWithSounds(pieceID, pageRequest));
    }
}
