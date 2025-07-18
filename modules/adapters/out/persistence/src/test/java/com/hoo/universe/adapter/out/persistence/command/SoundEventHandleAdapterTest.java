package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.universe.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.universe.adapter.out.persistence.repository.SoundJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.sound.SoundCreateEvent;
import com.hoo.universe.domain.event.sound.SoundDeleteEvent;
import com.hoo.universe.domain.event.sound.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.sound.SoundMetadataUpdateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SoundMetadata;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class SoundEventHandleAdapterTest {

    @Autowired
    SoundEventHandleAdapter sut;

    @Autowired
    SoundJpaRepository soundJpaRepository;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    LoadUniversePort loadUniversePort;

    @Test
    @DisplayName("사운드 생성 이벤트 처리")
    void handleSoundCreateEvent() {
        // given
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeJpaRepository.findUuidById(1L));
        Piece piece = universe.getPieces().getFirst();
        SoundCreateEvent event = piece.createSoundInside(
                new Sound.SoundID(UUID.randomUUID()),
                SoundMetadata.create(
                        UUID.randomUUID(),
                        false
                ),
                CommonMetadata.create("소리", "사운드는 소리입니다.")
        );

        // when
        sut.handleSoundCreateEvent(event);
        SoundJpaEntity soundJpaEntity = findSoundJpaEntity(event.newSound());

        // then
        assertThat(soundJpaEntity.getUuid()).isEqualTo(event.newSound().getId().uuid());
    }

    @Test
    @DisplayName("사운드 상세정보 수정 이벤트 처리")
    void handleSoundMetadataUpdateEvent() {
        // given
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeJpaRepository.findUuidById(1L));
        Sound sound = universe.getSpaces().getLast().getSpaces().getFirst().getPieces().getFirst().getSounds().getFirst();
        SoundMetadataUpdateEvent event = sound.updateMetadata("수정", null, true);

        // when
        sut.handleSoundMetadataUpdateEvent(event);
        SoundJpaEntity soundJpaEntity = findSoundJpaEntity(sound);

        // then
        assertThat(soundJpaEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
        assertThat(soundJpaEntity.getCommonMetadata().getDescription()).isEqualTo("잔잔한 시냇물 소리를 담았습니다.");
        assertThat(soundJpaEntity.getSoundMetadata().getHidden()).isTrue();
    }

    @Test
    @DisplayName("사운드 파일 덮어쓰기 이벤트 처리")
    void handleSoundFileOverwriteEvent() {
        // given
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeJpaRepository.findUuidById(1L));
        Sound sound = universe.getSpaces().getLast().getSpaces().getFirst().getPieces().getFirst().getSounds().getFirst();
        UUID newAudioID = UUID.randomUUID();
        SoundFileOverwriteEvent event = sound.overwriteFile(newAudioID);

        // when
        sut.handleSoundFileOverwriteEvent(event);
        SoundJpaEntity soundJpaEntity = findSoundJpaEntity(sound);

        // then
        assertThat(soundJpaEntity.getSoundMetadata().getAudioFileID()).isEqualTo(newAudioID);
    }

    @Test
    @DisplayName("사운드 삭제 이벤트 처리")
    void handleSoundDeleteEvent() {
        // given
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeJpaRepository.findUuidById(1L));
        Sound sound = universe.getSpaces().getLast().getSpaces().getFirst().getPieces().getFirst().getSounds().getFirst();
        SoundDeleteEvent event = sound.delete();

        // when
        sut.handleSoundDeleteEvent(event);

        // then
        assertThat(soundJpaRepository.findByUuid(sound.getId().uuid())).isEmpty();
    }

    private SoundJpaEntity findSoundJpaEntity(Sound sound) {
        return soundJpaRepository.findByUuid(sound.getId().uuid()).orElseThrow();
    }

}