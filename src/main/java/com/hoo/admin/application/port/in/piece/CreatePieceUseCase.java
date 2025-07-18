package com.hoo.admin.application.port.in.piece;

public interface CreatePieceUseCase {
    CreatePieceResult create(CreatePieceCommand command);
}
