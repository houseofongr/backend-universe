package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.MoveSpaceWithTwoPointCommand;
import com.hoo.universe.api.in.dto.MoveSpaceWithTwoPointResult;
import com.hoo.universe.api.in.MoveSpaceUseCase;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.UpdateSpaceStatusPort;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.SpaceMoveEvent;
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
public class MoveSpaceService implements MoveSpaceUseCase {

    private final LoadUniversePort loadUniversePort;
    private final UpdateSpaceStatusPort updateSpaceStatusPort;

    @Override
    public MoveSpaceWithTwoPointResult moveSpaceWithTwoPoint(UUID universeID, UUID spaceID, MoveSpaceWithTwoPointCommand command) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);

        Outline rectangle = Outline.getRectangleBy2Point(Point.of(command.startX(), command.startY()), Point.of(command.endX(), command.endY()));
        SpaceMoveEvent event = universe.moveSpace(new SpaceID(spaceID), rectangle);

        if (event.overlapEvent().isOverlapped()) throw new UniverseDomainException(DomainErrorCode.OVERLAPPED);

        updateSpaceStatusPort.updateSpaceMove(event);

        List<Point> points = rectangle.getPoints();
        Point[] farthestPoints = rectangle.getRectangleFarthestPoints();

        return new MoveSpaceWithTwoPointResult(
                farthestPoints[0].x(),
                farthestPoints[0].y(),
                farthestPoints[1].x(),
                farthestPoints[1].y()
        );
    }
}
