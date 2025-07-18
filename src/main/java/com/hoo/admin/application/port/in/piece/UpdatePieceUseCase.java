package com.hoo.admin.application.port.in.piece;

public interface UpdatePieceUseCase {
    UpdatePieceResult.Detail updateDetail(Long pieceId, UpdatePieceCommand.Detail command);

    UpdatePieceResult.Position updatePosition(Long pieceId, UpdatePieceCommand.Position command);
}
