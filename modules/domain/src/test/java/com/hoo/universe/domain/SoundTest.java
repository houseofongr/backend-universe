package com.hoo.universe.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.event.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.SoundMetadataUpdateEvent;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.hoo.universe.test.domain.SoundTestData.defaultSound;
import static org.assertj.core.api.Assertions.assertThat;

class SoundTest {

    @Test
    @DisplayName("사운드 상세정보 수정")
    void updateSoundMetadata() {
        // given
        Sound sound = defaultSound().build();

        String title = "수정";
        String description = "수정된 내용";
        Boolean hidden = true;

        // when
        SoundMetadataUpdateEvent event = sound.updateMetadata(null, null, hidden);
        SoundMetadataUpdateEvent event2 = sound.updateMetadata(title, description, null);

        // then
        assertThat(event.title()).isEqualTo("소리");
        assertThat(event.description()).isEqualTo("사운드는 소리입니다.");
        assertThat(event.hidden()).isTrue();
        assertThat(event.updatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));

        assertThat(event2.title()).isEqualTo(title);
        assertThat(event2.description()).isEqualTo(description);
        assertThat(event2.hidden()).isTrue();

        assertThat(sound.getCommonMetadata().getTitle()).isEqualTo(title);
        assertThat(sound.getCommonMetadata().getDescription()).isEqualTo(description);
        assertThat(sound.getCommonMetadata().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));
        assertThat(sound.getSoundMetadata().isHidden()).isTrue();
    }

    @Test
    @DisplayName("사운드 파일 덮어쓰기")
    void overwriteSoundFile() {
        // given
        Sound sound = defaultSound().build();
        UUID oldAudioID = sound.getSoundMetadata().getAudioID();

        // when
        UUID newAudioID = UuidCreator.getTimeOrderedEpoch();
        SoundFileOverwriteEvent event = sound.overwriteFile(newAudioID);

        // then
        assertThat(event.oldAudioID()).isEqualTo(oldAudioID);
        assertThat(event.newAudioID()).isEqualTo(newAudioID);
    }

}