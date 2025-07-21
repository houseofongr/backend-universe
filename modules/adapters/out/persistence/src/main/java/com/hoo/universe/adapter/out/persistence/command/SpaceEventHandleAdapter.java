package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.OutlineSerializer;
import com.hoo.universe.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.SoundJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.SpaceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.api.out.HandleSpaceEventPort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.event.space.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpaceEventHandleAdapter implements HandleSpaceEventPort {

    private final UniverseJpaRepository universeJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    private final PieceJpaRepository pieceJpaRepository;
    private final SoundJpaRepository soundJpaRepository;
    private final OutlineSerializer outlineSerializer;

    @Override
    public void handleSpaceCreateEvent(SpaceCreateEvent event) {

        Space newSpace = event.newSpace();
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(newSpace.getUniverseID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(newSpace.getOutline());
        SpaceJpaEntity spaceJpaEntity = SpaceJpaEntity.createNewEntity(newSpace, universeJpaEntity, outlinePoints);

        spaceJpaRepository.save(spaceJpaEntity);
    }

    @Override
    public void handleSpaceMetadataUpdateEvent(SpaceMetadataUpdateEvent event) {

        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        spaceJpaEntity.applyEvent(event);
    }

    @Override
    public void handleSpaceFileOverwriteEvent(SpaceFileOverwriteEvent event) {

        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        spaceJpaEntity.applyEvent(event);
    }

    @Override
    public void handleSpaceMoveEvent(SpaceMoveEvent event) {

        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(event.outline());

        spaceJpaEntity.move(outlinePoints);
    }

    @Override
    public void handleSpaceDeleteEvent(SpaceDeleteEvent event) {

        spaceJpaRepository.deleteAllByUuidIn(event.deleteSpaceIDs().stream().map(Space.SpaceID::uuid).toList());
        pieceJpaRepository.deleteAllByUuidIn(event.deletePieceIDs().stream().map(Piece.PieceID::uuid).toList());
        soundJpaRepository.deleteAllByUuidIn(event.deleteSoundIDs().stream().map(Sound.SoundID::uuid).toList());
    }
}
