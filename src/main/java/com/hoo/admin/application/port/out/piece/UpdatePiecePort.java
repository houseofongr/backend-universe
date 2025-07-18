package com.hoo.admin.application.port.out.piece;

import com.hoo.admin.domain.universe.piece.Piece;

public interface UpdatePiecePort {
    void update(Long pieceId, Piece piece);
}
