package com.hoo.universe.application;

import com.hoo.common.IssueIDPort;
import com.hoo.common.enums.AccessLevel;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;
import com.hoo.common.internal.api.dto.UserInfo;
import com.hoo.universe.api.in.dto.CreateUniverseCommand;
import com.hoo.universe.api.in.dto.CreateUniverseResult;
import com.hoo.universe.api.in.CreateUniverseUseCase;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.out.HandleUniverseEventPort;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.common.internal.api.GetUserInfoAPI;
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
    private final GetUserInfoAPI getOwnerAPI;
    private final QueryCategoryPort queryCategoryPort;
    private final HandleUniverseEventPort handleUniverseEventPort;
    private final UploadFileAPI uploadFileAPI;

    @Override
    public CreateUniverseResult createNewUniverse(CreateUniverseCommand command) {

        validate(command);

        UserInfo userInfo = getOwnerAPI.getUserInfo(command.metadata().ownerID());
        Owner owner = new Owner(userInfo.id(), userInfo.nickname());
        Category category = queryCategoryPort.findUniverseCategory(command.metadata().categoryID());
        AccessLevel accessLevel = AccessLevel.valueOf(command.metadata().accessLevel());

        UploadFileResult thumbmusic = uploadFileAPI.uploadFile(UploadFileCommand.from(command.thumbmusic(), owner.getId(), accessLevel));
        UploadFileResult thumbnail = uploadFileAPI.uploadFile(UploadFileCommand.from(command.thumbnail(), owner.getId(), accessLevel));
        UploadFileResult background = uploadFileAPI.uploadFile(UploadFileCommand.from(command.background(), owner.getId(), accessLevel));

        UniverseID newUniverseID = new UniverseID(issueIDPort.issueNewID());

        UniverseCreateEvent event = Universe.create(
                newUniverseID,
                category,
                owner,
                UniverseMetadata.create(
                        thumbmusic.id(),
                        thumbnail.id(),
                        background.id(),
                        AccessLevel.valueOf(command.metadata().accessLevel()),
                        command.metadata().hashtags()),
                CommonMetadata.create(
                        command.metadata().title(),
                        command.metadata().description())
        );

        handleUniverseEventPort.handleCreateUniverseEvent(event);
        Universe universe = event.newUniverse();

        return new CreateUniverseResult(
                universe.getId().uuid(),
                thumbmusic.fileUrl(),
                thumbnail.fileUrl(),
                background.fileUrl(),
                universe.getOwner().getId(),
                universe.getCommonMetadata().getCreatedTime().toEpochSecond(),
                universe.getCategory().getId(),
                universe.getCommonMetadata().getTitle(),
                universe.getCommonMetadata().getDescription(),
                universe.getOwner().getNickname(),
                universe.getUniverseMetadata().getAccessLevel().name(),
                universe.getUniverseMetadata().getTags()
        );
    }

    private void validate(CreateUniverseCommand command) {
        if (command.thumbnail().size() > 2 * 1024 * 1024 ||
            command.thumbmusic().size() > 2 * 1024 * 1024 ||
            command.background().size() > 100 * 1024 * 1024
        )
            throw new UniverseApplicationException(ApplicationErrorCode.EXCEEDED_FILE_SIZE);
    }
}
