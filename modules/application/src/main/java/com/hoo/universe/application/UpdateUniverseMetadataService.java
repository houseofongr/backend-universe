package com.hoo.universe.application;

import com.hoo.common.enums.AccessLevel;
import com.hoo.common.internal.api.user.GetUserInfoAPI;
import com.hoo.common.internal.api.user.dto.UserInfo;
import com.hoo.universe.api.in.dto.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.in.dto.UpdateUniverseMetadataResult;
import com.hoo.universe.api.in.UpdateUniverseMetadataUseCase;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.HandleUniverseEventPort;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.vo.Owner;
import com.hoo.universe.domain.vo.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUniverseMetadataService implements UpdateUniverseMetadataUseCase {

    private final LoadUniversePort loadUniversePort;
    private final GetUserInfoAPI getUserInfoAPI;
    private final QueryCategoryPort queryCategoryPort;
    private final HandleUniverseEventPort handleUniverseEventPort;

    @Override
    public UpdateUniverseMetadataResult updateUniverseMetadata(UUID universeID, UpdateUniverseMetadataCommand command) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);
        UserInfo userInfo = getUserInfoAPI.getUserInfo(command.ownerID());
        Owner owner = new Owner(userInfo.id(), userInfo.nickname());
        Category category = queryCategoryPort.findUniverseCategory(command.categoryID());

        UniverseMetadataUpdateEvent event = universe.updateMetadata(category, owner, command.title(), command.description(), AccessLevel.valueOf(command.accessLevel()), command.hashtags());

        handleUniverseEventPort.handleUniverseMetadataUpdateEvent(event);

        return new UpdateUniverseMetadataResult(
                universe.getOwner().getId(),
                universe.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                universe.getCommonMetadata().getTitle(),
                universe.getCommonMetadata().getDescription(),
                universe.getOwner().getNickname(),
                universe.getUniverseMetadata().getAccessLevel().name(),
                universe.getCategory(),
                universe.getUniverseMetadata().getTags()
        );
    }
}
