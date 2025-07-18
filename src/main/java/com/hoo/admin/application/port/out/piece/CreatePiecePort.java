package com.hoo.admin.application.port.out.piece;

import com.hoo.admin.application.port.in.piece.CreatePieceCommand;
import com.hoo.admin.domain.universe.piece.Piece;

public interface CreatePiecePort {
    Piece createPieceWithoutImageFile(CreatePieceCommand command);
}
