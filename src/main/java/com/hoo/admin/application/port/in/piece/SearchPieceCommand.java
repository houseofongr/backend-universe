package com.hoo.admin.application.port.in.piece;

import org.springframework.data.domain.Pageable;

public record SearchPieceCommand(
        Pageable pageable,
        Long pieceId,
        String sortType,
        Boolean isAsc
) {
}
