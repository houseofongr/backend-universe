package com.hoo.universe.application;

import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.dto.UploadFileResponse;
import com.hoo.universe.api.dto.command.CreateUniverseCommand;
import com.hoo.universe.api.dto.result.CreateUniverseResult;
import com.hoo.universe.api.in.CreateUniverseUseCase;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
import com.hoo.universe.api.out.internal.FindAuthorAPI;
import com.hoo.universe.application.exception.UniverseApplicationException;
import com.hoo.universe.application.exception.ApplicationErrorCode;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.event.UniverseCreateEvent;
import com.hoo.universe.domain.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUniverseService implements CreateUniverseUseCase {

    private final IssueIDPort issueIDPort;
    private final FindAuthorAPI findAuthorAPI;
    private final QueryCategoryPort queryCategoryPort;
    private final HandleUniverseEventPort handleUniverseEventPort;
    private final FileUploadAPI fileUploadAPI;

    @Override
    public CreateUniverseResult createNewUniverse(CreateUniverseCommand command) {

        validate(command);

        Author author = findAuthorAPI.findAuthor(command.metadata().authorID());
        Category category = queryCategoryPort.findUniverseCategory(command.metadata().categoryID());

        UploadFileResponse thumbmusic = fileUploadAPI.uploadFile(command.thumbMusic());
        UploadFileResponse thumbnail = fileUploadAPI.uploadFile(command.thumbnail());
        UploadFileResponse background = fileUploadAPI.uploadFile(command.background());

        UniverseID newUniverseID = new UniverseID(issueIDPort.issueNewID());

        UniverseCreateEvent event = Universe.create(
                newUniverseID,
                category,
                author,
                UniverseMetadata.create(
                        thumbmusic.id(),
                        thumbnail.id(),
                        background.id(),
                        AccessStatus.of(command.metadata().accessStatus()),
                        command.metadata().hashtags()),
                CommonMetadata.create(
                        command.metadata().title(),
                        command.metadata().description())
        );

        handleUniverseEventPort.handleCreateUniverseEvent(event);
        Universe universe = event.newUniverse();

        return new CreateUniverseResult(
                universe.getId().uuid(),
                universe.getUniverseMetadata().getThumbmusicID(),
                universe.getUniverseMetadata().getThumbnailID(),
                universe.getUniverseMetadata().getBackgroundID(),
                universe.getAuthor().getId(),
                universe.getCommonMetadata().getCreatedTime().toEpochSecond(),
                universe.getCategory().getId(),
                universe.getCommonMetadata().getTitle(),
                universe.getCommonMetadata().getDescription(),
                universe.getAuthor().getNickname(),
                universe.getUniverseMetadata().getAccessStatus().name(),
                universe.getUniverseMetadata().getTags()
        );
    }

    private void validate(CreateUniverseCommand command) {
        if (command.thumbnail().size() > 2 * 1024 * 1024 ||
            command.thumbMusic().size() > 2 * 1024 * 1024 ||
            command.background().size() > 100 * 1024 * 1024
        )
            throw new UniverseApplicationException(ApplicationErrorCode.EXCEEDED_FILE_SIZE);
    }
}
