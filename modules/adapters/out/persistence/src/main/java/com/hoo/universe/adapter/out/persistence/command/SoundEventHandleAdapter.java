package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.SoundJpaRepository;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.event.sound.SoundCreateEvent;
import com.hoo.universe.domain.event.sound.SoundDeleteEvent;
import com.hoo.universe.domain.event.sound.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.sound.SoundMetadataUpdateEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SoundEventHandleAdapter implements HandleSoundEventPort {

    private final PieceJpaRepository pieceJpaRepository;
    private final SoundJpaRepository soundJpaRepository;

    @Override
    public void handleSoundCreateEvent(SoundCreateEvent event) {

        Sound newSound = event.newSound();
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(newSound.getParentPieceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        SoundJpaEntity soundJpaEntity = SoundJpaEntity.createNewEntity(newSound, pieceJpaEntity);

        soundJpaRepository.save(soundJpaEntity);
    }

    @Override
    public void handleSoundMetadataUpdateEvent(SoundMetadataUpdateEvent event) {

        SoundJpaEntity soundJpaEntity = soundJpaRepository.findByUuid(event.soundID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        soundJpaEntity.applyEvent(event);
    }

    @Override
    public void handleSoundFileOverwriteEvent(SoundFileOverwriteEvent event) {

        SoundJpaEntity soundJpaEntity = soundJpaRepository.findByUuid(event.soundID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        soundJpaEntity.applyEvent(event);
    }

    @Override
    public void handleSoundDeleteEvent(SoundDeleteEvent event) {

        soundJpaRepository.deleteByUuid(event.deleteSoundID().uuid());
    }
}
