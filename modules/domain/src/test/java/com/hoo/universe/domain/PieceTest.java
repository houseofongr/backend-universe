package com.hoo.universe.domain;

import com.hoo.universe.domain.event.PieceDeleteEvent;
import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static com.hoo.universe.test.domain.PieceTestData.defaultPiece;
import static com.hoo.universe.test.domain.SoundTestData.*;
import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Test
    @DisplayName("사운드 100개 이상 소유 불가")
    void max100() {
        Piece piece = defaultPiece().build();

        for (int i = 0; i < 100; i++) {
            assertThat(piece.createSoundInside(
                            defaultSoundID(),
                            defaultSoundMetadata(),
                            defaultSoundCommonMetadata()
                    ).maxSoundExceeded()
            ).isFalse();
        }

        assertThat(piece.createSoundInside(
                        defaultSoundID(),
                        defaultSoundMetadata(),
                        defaultSoundCommonMetadata()
                ).maxSoundExceeded()
        ).isTrue();
    }

    @Test
    @DisplayName("사운드 추가")
    void addSound() {
        // given
        Piece piece = defaultPiece().build();
        Sound sound = defaultSound().build();

        // when
        piece.createSoundInside(defaultSoundID(), defaultSoundMetadata(), defaultSoundCommonMetadata());

        // then
        assertThat(piece.getSounds()).hasSize(1);
    }

    @Test
    @DisplayName("피스 상세정보 수정")
    void updatePieceMetadata() {
        // given
        Piece piece = defaultPiece().build();

        String title = "수정";
        String description = "수정된 내용";
        Boolean hidden = true;

        // when
        PieceMetadataUpdateEvent event = piece.updateMetadata(null, null, hidden);
        PieceMetadataUpdateEvent event2 = piece.updateMetadata(title, description, null);

        // then
        assertThat(event.title()).isEqualTo("조각");
        assertThat(event.description()).isEqualTo("피스는 조각입니다.");
        assertThat(event.hidden()).isTrue();
        assertThat(event.updatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));

        assertThat(event2.title()).isEqualTo(title);
        assertThat(event2.description()).isEqualTo(description);
        assertThat(event2.hidden()).isTrue();

        assertThat(piece.getCommonMetadata().getTitle()).isEqualTo(title);
        assertThat(piece.getCommonMetadata().getDescription()).isEqualTo(description);
        assertThat(piece.getCommonMetadata().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));
        assertThat(piece.getPieceMetadata().isHidden()).isTrue();
    }

    @Test
    @DisplayName("피스 삭제")
    void deletePiece() {
        // given
        Universe universe = defaultUniverse();
        Piece piece1 = universe.getPieces().getFirst();

        // when
        PieceDeleteEvent event = piece1.delete();

        // then
        assertThat(event.deletePieceID()).isEqualTo(piece1.getId());
        assertThat(event.deleteSoundIDs()).hasSize(2);
        assertThat(event.deleteFileIDs()).hasSize(2);
    }

}