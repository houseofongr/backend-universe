package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListResult;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({SoundSourcePersistenceAdapter.class, SoundSourceMapper.class})
@Sql("SoundSourcePersistenceAdapterTest.sql")
class SoundSourcePersistenceAdapterTest {

    @Autowired
    SoundSourcePersistenceAdapter sut;

    @Autowired
    SoundSourceJpaRepository soundSourceJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("음원 저장 테스트")
    void testSaveSoundSource() {
        // given
        SoundSource soundSource = SoundSource.create(1L, 1L, 1L, "골골송", "2025년 골골송 V1", true);

        // when
        Long savedSoundSourceId = sut.saveSoundSource(soundSource);

        Optional<SoundSourceJpaEntity> optional = soundSourceJpaRepository.findById(savedSoundSourceId);
        assertThat(optional).isNotEmpty();
        SoundSourceJpaEntity soundSourceJpaEntity = optional.get();

        // then
        assertThat(soundSourceJpaEntity.getItem().getId()).isEqualTo(1L);
        assertThat(soundSourceJpaEntity.getItem().getSoundSources().getLast()).usingRecursiveComparison().isEqualTo(soundSourceJpaEntity);

        assertThat(soundSourceJpaEntity.getName()).isEqualTo(soundSource.getSoundSourceDetail().getName());
        assertThat(soundSourceJpaEntity.getDescription()).isEqualTo(soundSource.getSoundSourceDetail().getDescription());
        assertThat(soundSourceJpaEntity.getIsActive()).isEqualTo(soundSource.getActive().isActive());
        assertThat(soundSourceJpaEntity.getAudioFileId()).isEqualTo(soundSource.getFile().getFileId().getId());
        assertThat(savedSoundSourceId).isNotNull();
    }

    @Test
    @DisplayName("전체 음원 조회 테스트")
    void testQueryAllSoundSource() {
        // given
        QuerySoundSourceListCommand command = new QuerySoundSourceListCommand(PageRequest.of(0, 3));

        // when
        QuerySoundSourceListResult result = sut.querySoundSourceList(command);

        // then
        assertThat(result.soundSources()).hasSize(3);
        assertThat(result.soundSources()).anySatisfy(soundSourceInfo -> {
            assertThat(soundSourceInfo.name()).isEqualTo("골골송");
            assertThat(soundSourceInfo.description()).isEqualTo("2025년 골골송 V1");
            assertThat(soundSourceInfo.createdDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
            assertThat(soundSourceInfo.updatedDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
            assertThat(soundSourceInfo.audioFileId()).isEqualTo(1);
            assertThat(soundSourceInfo.homeName()).isEqualTo("leaf의 cozy house");
            assertThat(soundSourceInfo.homeId()).isEqualTo(1);
            assertThat(soundSourceInfo.roomName()).isEqualTo("거실");
            assertThat(soundSourceInfo.roomId()).isEqualTo(1);
            assertThat(soundSourceInfo.itemName()).isEqualTo("설이");
            assertThat(soundSourceInfo.itemId()).isEqualTo(1);
        }).anySatisfy(soundSourceInfo -> {
            assertThat(soundSourceInfo.name()).isEqualTo("골골송2");
            assertThat(soundSourceInfo.description()).isEqualTo("2025년 골골송 V2");
        }).anySatisfy(soundSourceInfo -> {
            assertThat(soundSourceInfo.name()).isEqualTo("골골송3");
            assertThat(soundSourceInfo.description()).isEqualTo("2025년 골골송 V3");
        });
    }

    @Test
    @DisplayName("음원 조회 테스트")
    void testLoadSoundSource() {
        // given
        Long soundSourceId = 1L;

        // when
        Optional<SoundSource> soundSource = sut.loadSoundSource(soundSourceId);
        Optional<SoundSource> nullSound = sut.loadSoundSource(1234L);

        // then
        assertThat(nullSound).isEmpty();
        assertThat(soundSource).isNotEmpty();
        assertThat(soundSource.get().getSoundSourceDetail().getName()).isEqualTo("골골송");
    }

    @Test
    @DisplayName("음원 수정 테스트")
    void testUpdateSoundSource() throws InterruptedException {
        // given
        SoundSource soundSource = SoundSource.create(1L, 1L, 1L, "골골골송", "2026년 설이가 보내는 골골골송", false);

        // when
        sut.updateSoundSource(soundSource);
        entityManager.flush();
        entityManager.clear();

        Optional<SoundSourceJpaEntity> optional = soundSourceJpaRepository.findById(1L);
        assertThat(optional).isNotEmpty();
        SoundSourceJpaEntity soundSourceInDB = optional.get();

        // then
        assertThat(soundSourceInDB.getName()).isEqualTo("골골골송");
        assertThat(soundSourceInDB.getDescription()).isEqualTo("2026년 설이가 보내는 골골골송");
        assertThat(soundSourceInDB.getIsActive()).isFalse();
    }

    @Test
    @DisplayName("음원 삭제 테스트")
    void testDeleteSoundSource() {
        // given
        SoundSource soundSource = SoundSource.create(1L, 1L, 1L, "골골골송", "2026년 설이가 보내는 골골골송", false);

        // when
        sut.deleteSoundSource(soundSource);

        // then
        assertThat(soundSourceJpaRepository.findById(1L)).isEmpty();
    }
}