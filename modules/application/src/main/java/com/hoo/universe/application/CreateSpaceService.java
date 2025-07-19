package com.hoo.universe.application;

import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.in.web.dto.command.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.api.in.web.dto.result.CreateSpaceResult;
import com.hoo.universe.api.in.web.usecase.CreateSpaceUseCase;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.application.exception.ApplicationErrorCode;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseApplicationException;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.space.SpaceCreateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;
import com.hoo.universe.domain.vo.SpaceMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSpaceService implements CreateSpaceUseCase {

    private final IssueIDPort issueIDPort;
    private final LoadUniversePort loadUniversePort;
    private final HandleSpaceEventPort handleSpaceEventPort;
    private final UploadFileAPI uploadFileAPI;

    @Override
    public CreateSpaceResult createSpaceWithTwoPoint(UUID universeID, UUID parentSpaceID, CreateSpaceWithTwoPointCommand command) {

        validate(command);

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        SpaceID newSpaceID = new SpaceID(issueIDPort.issueNewID());

        UploadFileResult background = uploadFileAPI.uploadFile(UploadFileCommand.from(command.background(), universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel()));

        SpaceCreateEvent event = universe.createSpaceInside(newSpaceID, new SpaceID(parentSpaceID),
                SpaceMetadata.create(background.id(), command.metadata().hidden()),
                CommonMetadata.create(command.metadata().title(), command.metadata().description()),
                Outline.getRectangleBy2Point(
                        Point.of(command.metadata().startX(), command.metadata().startY()),
                        Point.of(command.metadata().endX(), command.metadata().endY())
                )
        );

        if (event.maxChildExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_CHILD_EXCEED);
        if (event.maxSpaceExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_SPACE_EXCEED);
        if (event.overlapEvent().isOverlapped())
            throw new UniverseDomainException(DomainErrorCode.OVERLAPPED, DomainErrorCode.OVERLAPPED.getMessage() + event.overlapEvent().renderOverlapStatus());

        Space newSpace = event.newSpace();
        handleSpaceEventPort.handleSpaceCreateEvent(event);
        Point[] farthestPoints = newSpace.getOutline().getRectangleFarthestPoints();

        return new CreateSpaceResult(
                newSpace.getId().uuid(),
                background.fileUrl(),
                newSpace.getCommonMetadata().getTitle(),
                newSpace.getCommonMetadata().getDescription(),
                farthestPoints[0].x(),
                farthestPoints[0].y(),
                farthestPoints[1].x(),
                farthestPoints[1].y(),
                newSpace.getSpaceMetadata().isHidden(),
                newSpace.getCommonMetadata().getCreatedTime().toEpochSecond()
        );
    }

    private void validate(CreateSpaceWithTwoPointCommand command) {
        if (command.background().size() > 100 * 1024 * 1024)
            throw new UniverseApplicationException(ApplicationErrorCode.EXCEEDED_FILE_SIZE);
    }
}
