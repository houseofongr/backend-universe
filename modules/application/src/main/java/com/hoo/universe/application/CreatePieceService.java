package com.hoo.universe.application;

import com.hoo.common.IssueIDPort;
import com.hoo.universe.api.in.dto.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.in.dto.CreatePieceResult;
import com.hoo.universe.api.in.CreatePieceUseCase;
import com.hoo.universe.api.out.HandlePieceEventPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.piece.PieceCreateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatePieceService implements CreatePieceUseCase {

    private final IssueIDPort issueIDPort;
    private final LoadUniversePort loadUniversePort;
    private final HandlePieceEventPort handlePieceEventPort;

    @Override
    public CreatePieceResult createNewPieceWithTwoPoint(UUID universeID, CreatePieceWithTwoPointCommand command) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        PieceID newPieceID = new PieceID(issueIDPort.issueNewID());

        PieceCreateEvent event = universe.createPieceInside(newPieceID, new SpaceID(command.metadata().parentSpaceID()),
                PieceMetadata.create(null, command.metadata().hidden()),
                CommonMetadata.create(command.metadata().title(), command.metadata().description()),
                Outline.getRectangleBy2Point(
                        Point.of(command.metadata().startX(), command.metadata().startY()),
                        Point.of(command.metadata().endX(), command.metadata().endY()))
        );

        if (event.maxChildExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_CHILD_EXCEED);
        if (event.overlapEvent().isOverlapped()) throw new UniverseDomainException(DomainErrorCode.OVERLAPPED, DomainErrorCode.OVERLAPPED.getMessage() + event.overlapEvent().renderOverlapStatus());

        Piece newPiece = event.newPiece();
        handlePieceEventPort.handlePieceCreateEvent(event);
        Point[] farthestPoints = newPiece.getOutline().getRectangleFarthestPoints();

        return new CreatePieceResult(
                newPiece.getId().uuid(),
                newPiece.getCommonMetadata().getTitle(),
                newPiece.getCommonMetadata().getDescription(),
                farthestPoints[0].x(),
                farthestPoints[0].y(),
                farthestPoints[1].x(),
                farthestPoints[1].y(),
                newPiece.getPieceMetadata().isHidden(),
                newPiece.getCommonMetadata().getCreatedTime().toEpochSecond()
        );
    }
}
