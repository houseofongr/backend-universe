package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.MovePieceWithTwoPointCommand;
import com.hoo.universe.api.in.dto.MovePieceWithTwoPointResult;
import com.hoo.universe.api.in.MovePieceUseCase;
import com.hoo.universe.api.out.HandlePieceEventPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.piece.PieceMoveEvent;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MovePieceService implements MovePieceUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandlePieceEventPort handlePieceEventPort;

    @Override
    public MovePieceWithTwoPointResult movePieceWithTwoPoint(UUID universeID, UUID pieceID, MovePieceWithTwoPointCommand command) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);

        Outline rectangle = Outline.getRectangleBy2Point(Point.of(command.startX(), command.startY()), Point.of(command.endX(), command.endY()));
        PieceMoveEvent event = universe.movePiece(new Piece.PieceID(pieceID), rectangle);

        if (event.overlapEvent().isOverlapped()) throw new UniverseDomainException(DomainErrorCode.OVERLAPPED);

        handlePieceEventPort.handlePieceMoveEvent(event);

        List<Point> points = rectangle.getPoints();
        Point[] farthestPoints = rectangle.getRectangleFarthestPoints();

        return new MovePieceWithTwoPointResult(
                farthestPoints[0].x(),
                farthestPoints[0].y(),
                farthestPoints[1].x(),
                farthestPoints[1].y()
        );
    }
}
