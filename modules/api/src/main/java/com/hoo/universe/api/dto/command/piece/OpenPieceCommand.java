package com.hoo.universe.api.dto.command.piece;

import com.hoo.common.web.dto.PageRequest;

import java.util.UUID;

public record OpenPieceCommand(
        PageRequest pageRequest,
        UUID pieceID
) {
}
