package com.hoo.universe.application;

import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.api.dto.command.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.dto.result.UpdateUniverseMetadataResult;
import com.hoo.universe.api.in.UpdateUniverseMetadataUseCase;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.internal.FindOwnerAPI;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
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
    private final FindOwnerAPI findOwnerAPI;
    private final QueryCategoryPort queryCategoryPort;
    private final HandleUniverseEventPort handleUniverseEventPort;

    @Override
    public UpdateUniverseMetadataResult updateUniverseMetadata(UUID universeID, UpdateUniverseMetadataCommand command) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);
        Owner owner = findOwnerAPI.findOwner(command.ownerID());
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
