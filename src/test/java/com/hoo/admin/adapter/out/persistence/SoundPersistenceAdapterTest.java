package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.SoundMapper;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.SoundJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Sql("classpath:sql/universe.sql")
@Import({SoundPersistenceAdapter.class, SoundMapper.class})
class SoundPersistenceAdapterTest {

    @Autowired
    SoundPersistenceAdapter sut;

    @Autowired
    SoundJpaRepository soundJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("사운드 저장 테스트")
    void testSaveSound() {
        // given
        Sound sound = Sound.create(123L, 345L, 1L, "소리", "사운드는 소리입니다.", false);

        // when
        Long newSoundId = sut.save(sound);
        SoundJpaEntity soundInDB = soundJpaRepository.findById(newSoundId).orElseThrow();

        // then
        assertThat(soundInDB.getId()).isEqualTo(newSoundId);
        assertThat(soundInDB.getAudioFileId()).isEqualTo(345L);
        assertThat(soundInDB.getPiece().getId()).isEqualTo(1L);
        assertThat(soundInDB.getTitle()).isEqualTo("소리");
        assertThat(soundInDB.getDescription()).isEqualTo("사운드는 소리입니다.");
    }

    @Test
    @DisplayName("사운드 조회 테스트")
    void testFindSound() {
        // given
        Long soundId = 1L;

        // when
        Sound sound = sut.find(soundId);
        SoundJpaEntity soundInDB = soundJpaRepository.findById(soundId).orElseThrow();

        // then
        assertThat(sound.getBasicInfo().getPieceId()).isNull();
        assertThat(sound.getBasicInfo().getTitle()).isEqualTo(soundInDB.getTitle());
        assertThat(sound.getBasicInfo().getDescription()).isEqualTo(soundInDB.getDescription());
        assertThat(sound.getDateInfo().getCreatedTime()).isEqualTo(soundInDB.getCreatedTime());
        assertThat(sound.getDateInfo().getUpdatedTime()).isEqualTo(soundInDB.getUpdatedTime());
    }

    @Test
    @DisplayName("사운드 업데이트 테스트")
    void testUpdateSound() {
        // given
        Sound sound = Sound.create(1L, 234L, 1L, "변경할 제목", "변경할 내용", false);

        // when
        sut.update(sound);
        em.flush();
        em.clear();
        SoundJpaEntity soundInDB = soundJpaRepository.findById(sound.getId()).orElseThrow();

        // then
        assertThat(soundInDB.getAudioFileId()).isEqualTo(234L);
        assertThat(soundInDB.getTitle()).isEqualTo("변경할 제목");
        assertThat(soundInDB.getDescription()).isEqualTo("변경할 내용");
        assertThat(soundInDB.getUpdatedTime()).isAfter(soundInDB.getCreatedTime());
    }

    @Test
    @DisplayName("사운드 삭제 테스트")
    void testDeleteSound() {
        // given
        Sound sound = Sound.create(1L, 234L, 1L, "변경할 제목", "변경할 내용", false);

        // when
        sut.delete(sound.getId());

        // then
        assertThat(soundJpaRepository.findById(sound.getId())).isEmpty();
    }
}