package com.hoo.admin.application.service.piece;

import com.hoo.admin.application.port.in.piece.CreatePieceCommand;
import com.hoo.admin.application.port.in.piece.CreatePieceResult;
import com.hoo.admin.application.port.in.piece.CreatePieceUseCase;
import com.hoo.admin.application.port.out.piece.CreatePiecePort;
import com.hoo.admin.application.port.out.piece.SavePiecePort;
import com.hoo.admin.domain.universe.piece.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatePieceService implements CreatePieceUseCase {

    private final CreatePiecePort createPiecePort;
    private final SavePiecePort savePiecePort;

    @Override
    public CreatePieceResult create(CreatePieceCommand command) {
        Piece newPiece = createPiecePort.createPieceWithoutImageFile(command);
        Long newPieceId = savePiecePort.save(newPiece);

        return new CreatePieceResult(
                String.format("[#%d]번 피스가 생성되었습니다.", newPieceId),
                newPieceId,
                newPiece.getBasicInfo().getTitle(),
                newPiece.getBasicInfo().getDescription(),
                newPiece.getPosInfo().getSx(),
                newPiece.getPosInfo().getSy(),
                newPiece.getPosInfo().getEx(),
                newPiece.getPosInfo().getEy(),
                newPiece.getBasicInfo().getHidden()
        );
    }
}
