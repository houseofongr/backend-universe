package com.hoo.universe.application;

import com.hoo.common.IssueIDPort;
import com.hoo.common.enums.AccessLevel;
import com.hoo.common.enums.Domain;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.api.file.dto.UploadFileResult;
import com.hoo.common.internal.api.user.GetUserInfoAPI;
import com.hoo.common.internal.api.user.dto.UserInfo;
import com.hoo.universe.api.in.CreateUniverseUseCase;
import com.hoo.universe.api.in.dto.CreateUniverseCommand;
import com.hoo.universe.api.in.dto.CreateUniverseResult;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.api.out.SaveEntityPort;
import com.hoo.universe.application.exception.ApplicationErrorCode;
import com.hoo.universe.application.exception.UniverseApplicationException;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.event.UniverseCreateEvent;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Owner;
import com.hoo.universe.domain.vo.UniverseMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUniverseService implements CreateUniverseUseCase {

    private final IssueIDPort issueIDPort;
    private final GetUserInfoAPI getUserInfoAPI;
    private final QueryCategoryPort queryCategoryPort;
    private final SaveEntityPort saveEntityPort;
    private final UploadFileAPI uploadFileAPI;

    @Override
    public CreateUniverseResult createNewUniverse(CreateUniverseCommand command) {

        validate(command);

        UserInfo userInfo = getUserInfoAPI.getUserInfo(command.metadata().ownerID());
        Owner owner = new Owner(userInfo.id(), userInfo.nickname());
        Category category = queryCategoryPort.findUniverseCategory(command.metadata().categoryID());
        AccessLevel accessLevel = AccessLevel.valueOf(command.metadata().accessLevel());

        UploadFileResult thumbmusic = uploadFileAPI.uploadFile(UploadFileCommand.from(command.thumbmusic(), Domain.UNIVERSE.getName(), owner.getId(), accessLevel));
        UploadFileResult thumbnail = uploadFileAPI.uploadFile(UploadFileCommand.from(command.thumbnail(), Domain.UNIVERSE.getName(), owner.getId(), accessLevel));
        UploadFileResult background = uploadFileAPI.uploadFile(UploadFileCommand.from(command.background(), Domain.UNIVERSE.getName(), owner.getId(), accessLevel));

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

        Universe newUniverse = event.newUniverse();
        saveEntityPort.saveUniverse(newUniverse);

        return new CreateUniverseResult(
                newUniverse.getId().uuid(),
                thumbmusic.fileUrl(),
                thumbnail.fileUrl(),
                background.fileUrl(),
                newUniverse.getOwner().getId(),
                newUniverse.getCommonMetadata().getCreatedTime().toEpochSecond(),
                newUniverse.getCategory().getId(),
                newUniverse.getCommonMetadata().getTitle(),
                newUniverse.getCommonMetadata().getDescription(),
                newUniverse.getOwner().getNickname(),
                newUniverse.getUniverseMetadata().getAccessLevel().name(),
                newUniverse.getUniverseMetadata().getTags()
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
