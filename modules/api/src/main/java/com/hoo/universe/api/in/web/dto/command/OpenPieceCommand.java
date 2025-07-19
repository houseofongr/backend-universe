package com.hoo.universe.api.in.web.dto.command;

import com.hoo.common.web.dto.PageRequest;

import java.util.UUID;

public record OpenPieceCommand(
        PageRequest pageRequest,
        UUID pieceID
) {
}
