package com.hoo.admin.application.port.out.piece;

import com.hoo.admin.application.port.in.piece.SearchPieceCommand;
import com.hoo.admin.application.port.in.piece.SearchPieceResult;
import com.hoo.admin.domain.universe.piece.Piece;

public interface FindPiecePort {
    Piece find(Long id);
    Piece findWithSounds(Long id);
    SearchPieceResult search(SearchPieceCommand command);
}
