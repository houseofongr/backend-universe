package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.entity.CategoryJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.TagJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.UniverseTagJpaEntity;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.adapter.out.persistence.repository.CategoryJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.TagJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.api.out.HandleUniverseEventPort;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.UniverseCreateEvent;
import com.hoo.universe.domain.event.UniverseDeleteEvent;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UniverseEventHandlerAdapter implements
        HandleUniverseEventPort {

    private final UniverseJpaRepository universeJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final TagJpaRepository tagJpaRepository;

    @Override
    public void handleCreateUniverseEvent(UniverseCreateEvent event) {

        Universe newUniverse = event.newUniverse();
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findByUuid(newUniverse.getCategory().getId())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.CATEGORY_NOT_FOUND));

        UniverseJpaEntity universeJpaEntity = UniverseJpaEntity.createNewEntity(newUniverse, categoryJpaEntity);
        addUniverseTagJpaEntities(universeJpaEntity, event.newUniverse().getUniverseMetadata().getTags());

        universeJpaRepository.save(universeJpaEntity);
    }

    @Override
    public void handleUniverseMetadataUpdateEvent(UniverseMetadataUpdateEvent event) {

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(event.universeID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        CategoryJpaEntity categoryJpaEntity = event.category() != null ?
                categoryJpaRepository.findByUuid(event.category().getId())
                        .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID))
                : null;

        universeJpaEntity.applyEvent(event, categoryJpaEntity);
    }

    @Override
    public void handleUniverseFileOverwriteEvent(UniverseFileOverwriteEvent event) {

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(event.universeID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        universeJpaEntity.applyEvent(event);
    }

    @Override
    public void handleUniverseDeleteEvent(UniverseDeleteEvent event) {
        universeJpaRepository.deleteByUuid(event.deleteUniverseID().uuid());
    }

    private void addUniverseTagJpaEntities(UniverseJpaEntity universeJpaEntity, List<String> tags) {

        List<String> existTags = tagJpaRepository.findExistTags(tags);
        List<String> notExistTags = tags.stream()
                .filter(tag -> !existTags.contains(tag))
                .toList();

        List<TagJpaEntity> newTagJpaEntities = createTags(notExistTags);
        List<UniverseTagJpaEntity> universeTagJpaEntities = newTagJpaEntities.stream()
                .map(tagJpaEntity -> UniverseTagJpaEntity.createNewEntity(universeJpaEntity, tagJpaEntity))
                .toList();

        universeJpaEntity.getUniverseHashtags().addAll(universeTagJpaEntities);
    }

    private List<TagJpaEntity> createTags(List<String> notExistTags) {

        List<TagJpaEntity> newTagJpaEntities = notExistTags.stream().map(TagJpaEntity::createNewEntity).toList();
        tagJpaRepository.saveAll(newTagJpaEntities);

        return newTagJpaEntities;
    }
}
