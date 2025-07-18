package com.hoo.admin.application.service.piece;

import com.hoo.admin.application.port.in.piece.UpdatePieceCommand;
import com.hoo.admin.application.port.in.piece.UpdatePieceResult;
import com.hoo.admin.application.port.in.piece.UpdatePieceUseCase;
import com.hoo.admin.application.port.out.piece.FindPiecePort;
import com.hoo.admin.application.port.out.piece.UpdatePiecePort;
import com.hoo.admin.domain.universe.piece.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePieceService implements UpdatePieceUseCase {

    private final FindPiecePort findPiecePort;
    private final UpdatePiecePort updatePiecePort;

    @Override
    public UpdatePieceResult.Detail updateDetail(Long pieceId, UpdatePieceCommand.Detail command) {
        Piece piece = findPiecePort.find(pieceId);

        piece.getBasicInfo().update(command.title(), command.description());
        piece.getBasicInfo().updateHiddenStatus(command.hidden());

        updatePiecePort.update(pieceId, piece);

        return UpdatePieceResult.Detail.of(piece);
    }

    @Override
    public UpdatePieceResult.Position updatePosition(Long pieceId, UpdatePieceCommand.Position command) {
        Piece piece = findPiecePort.find(pieceId);

        piece.getPosInfo().update(command.startX(), command.startY(), command.endX(), command.endY());
        updatePiecePort.update(pieceId, piece);

        return UpdatePieceResult.Position.of(piece);
    }
}
