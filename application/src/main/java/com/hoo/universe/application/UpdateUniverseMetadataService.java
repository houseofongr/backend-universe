package com.hoo.universe.application;

import com.hoo.universe.api.dto.command.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.dto.result.UpdateUniverseMetadataResult;
import com.hoo.universe.api.in.UpdateUniverseMetadataUseCase;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.internal.FindAuthorAPI;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.vo.AccessStatus;
import com.hoo.universe.domain.vo.Author;
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
    private final FindAuthorAPI findAuthorAPI;
    private final QueryCategoryPort queryCategoryPort;
    private final HandleUniverseEventPort handleUniverseEventPort;

    @Override
    public UpdateUniverseMetadataResult updateUniverseMetadata(UUID universeID, UpdateUniverseMetadataCommand command) {

        Universe universe = loadUniversePort.loadUniverseOnly(universeID);
        Author author = findAuthorAPI.findAuthor(command.authorID());
        Category category = queryCategoryPort.findUniverseCategory(command.categoryID());

        UniverseMetadataUpdateEvent event = universe.updateMetadata(category, author, command.title(), command.description(), AccessStatus.of(command.accessStatus()), command.hashtags());

        handleUniverseEventPort.handleUniverseMetadataUpdateEvent(event);

        return new UpdateUniverseMetadataResult(
                universe.getAuthor().getId(),
                universe.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                universe.getCommonMetadata().getTitle(),
                universe.getCommonMetadata().getDescription(),
                universe.getAuthor().getNickname(),
                universe.getUniverseMetadata().getAccessStatus().name(),
                universe.getCategory(),
                universe.getUniverseMetadata().getTags()
        );
    }
}
