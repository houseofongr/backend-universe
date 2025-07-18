package com.hoo.universe.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.event.piece.PieceCreateEvent;
import com.hoo.universe.domain.event.space.SpaceCreateEvent;
import com.hoo.universe.domain.event.space.SpaceDeleteEvent;
import com.hoo.universe.domain.event.space.SpaceFileOverwriteEvent;
import com.hoo.universe.domain.event.space.SpaceMetadataUpdateEvent;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.hoo.universe.test.domain.PieceTestData.defaultPiece;
import static com.hoo.universe.test.domain.SpaceTestData.defaultSpace;
import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.assertj.core.api.Assertions.assertThat;

class SpaceTest {

    @Test
    @DisplayName("내부 스페이스 생성")
    void createSpace() {
        // given
        Space space = defaultSpace().build();
        Space space2 = defaultSpace().build();

        // when
        SpaceCreateEvent result = space.addSpaceInside(space2);

        // then
        assertThat(result.newSpace()).isNotNull();
        assertThat(space.getSpaces()).hasSize(1);
    }

    @Test
    @DisplayName("같은 스페이스 확인 테스트")
    void testEquals() {
        // given
        UUID uuid = UuidCreator.getTimeOrderedEpoch();

        // when
        Space space1 = defaultSpace().withSpaceID(new SpaceID(uuid)).build();
        Space space2 = defaultSpace().withSpaceID(new SpaceID(uuid)).build();

        // then
        assertThat(space1).isEqualTo(space2);
    }

    @Test
    @DisplayName("피스 생성")
    void createPiece() {
        // given
        Space space = defaultSpace().build();
        Piece piece = defaultPiece().build();

        // when
        PieceCreateEvent result = space.addPieceInside(piece);

        // then
        assertThat(result.newPiece()).isNotNull();
        assertThat(space.getPieces()).hasSize(1);
    }

    @Test
    @DisplayName("스페이스 ID 비교(equals)")
    void equalsSpaceID() {
        // given
        UUID uuid = UuidCreator.getTimeOrderedEpoch();

        // when
        SpaceID spaceID1 = new SpaceID(uuid);
        SpaceID spaceID2 = new SpaceID(uuid);

        // then
        assertThat(spaceID1).isEqualTo(spaceID2);
    }

    @Test
    @DisplayName("스페이스 상세정보 수정")
    void updateSpaceMetadata() {
        // given
        Space space = defaultSpace().build();

        String title = "수정";
        String description = "수정된 내용";
        Boolean hidden = true;

        // when
        SpaceMetadataUpdateEvent event = space.updateMetadata(null, null, hidden);
        SpaceMetadataUpdateEvent event2 = space.updateMetadata(title, description, null);

        // then
        assertThat(event.title()).isEqualTo("공간");
        assertThat(event.description()).isEqualTo("스페이스는 공간입니다.");
        assertThat(event.hidden()).isTrue();
        assertThat(event.updatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));

        assertThat(event2.title()).isEqualTo(title);
        assertThat(event2.description()).isEqualTo(description);
        assertThat(event2.hidden()).isTrue();

        assertThat(space.getCommonMetadata().getTitle()).isEqualTo(title);
        assertThat(space.getCommonMetadata().getDescription()).isEqualTo(description);
        assertThat(space.getSpaceMetadata().isHidden()).isTrue();
    }

    @Test
    @DisplayName("스페이스 파일 덮어쓰기")
    void overwriteSpaceFile() {
        // given
        Space space = defaultSpace().build();
        UUID oldInnerImageID = space.getSpaceMetadata().getBackgroundID();

        // when
        UUID newInnerImageID = UuidCreator.getTimeOrderedEpoch();
        SpaceFileOverwriteEvent event = space.overwriteFile(newInnerImageID);

        // then
        assertThat(event.oldInnerImageID()).isEqualTo(oldInnerImageID);
        assertThat(event.newInnerImageID()).isEqualTo(newInnerImageID);
    }

    @Test
    @DisplayName("스페이스 삭제")
    void deleteSpace() {
        // given
        Universe universe = defaultUniverse();
        Space space2 = universe.getSpaces().getLast();

        // when
        SpaceDeleteEvent event = space2.delete();

        // then
        assertThat(event.deleteSpaceIDs()).hasSize(3);
        assertThat(event.deletePieceIDs()).hasSize(4);
        assertThat(event.deleteSoundIDs()).hasSize(6);
        assertThat(event.deleteFileIDs()).hasSize(9);
    }

}