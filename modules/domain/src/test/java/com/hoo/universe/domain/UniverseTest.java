package com.hoo.universe.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.event.UniverseDeleteEvent;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.event.PieceCreateEvent;
import com.hoo.universe.domain.event.SpaceCreateEvent;
import com.hoo.universe.domain.vo.*;
import com.hoo.universe.test.domain.PieceTestData;
import com.hoo.universe.test.domain.SpaceTestData;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.hoo.universe.test.domain.PieceTestData.*;
import static com.hoo.universe.test.domain.SpaceTestData.*;
import static com.hoo.universe.test.domain.UniverseTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class UniverseTest {

    @Test
    @DisplayName("스페이스 최대 10개생성 가능")
    void maxSpace10() {
        Universe universe = defaultUniverseOnly().build();

        for (int i = 0; i <= 10; i++) {
            double f = i * 0.01f;
            Outline outline = Outline.getRectangleBy2Point(Point.of(f, f), Point.of(f + 0.01f, f + 0.01f));

            if (i != 10)
                assertThat(universe.createSpaceInside(
                                defaultSpaceID(),
                                null,
                                defaultSpaceMetadata(),
                                defaultSpaceCommonMetadata(),
                                outline)
                        .maxSpaceExceeded()
                ).isFalse();

            else
                assertThat(universe.createSpaceInside(
                                defaultSpaceID(),
                                null,
                                defaultSpaceMetadata(),
                                defaultSpaceCommonMetadata(),
                                outline)
                        .maxSpaceExceeded()
                ).isTrue();
        }
    }

    @Test
    @DisplayName("스페이스 + 피스 최대 100개 생성 가능")
    void maxPiece100() {
        Universe universe = defaultUniverseOnly().build();

        for (int i = 0; i <= 100; i++) {
            double d = i * 0.001;
            Outline outline = Outline.getRectangleBy2Point(Point.of(d, d), Point.of(d + 0.001f, d + 0.001f));

            if (i != 100)
                assertThat(universe.createPieceInside(
                                new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()),
                                null,
                                defaultPieceMetadata(),
                                CommonMetadata.create("조각", "피스는 조각입니다."),
                                outline
                        ).maxChildExceeded()
                ).isFalse();

            else {
                assertThat(universe.createPieceInside(
                                new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()),
                                null,
                                defaultPieceMetadata(),
                                CommonMetadata.create("조각", "피스는 조각입니다."),
                                outline
                        ).maxChildExceeded()
                ).isTrue();
            }
        }
    }

    @Test
    @DisplayName("공간 겹칠 수 없음")
    void cannotOverlapped() {
        // given
        Universe universe = defaultUniverseOnly().build();
        Outline outline1 = Outline.getRectangleBy2Point(Point.of(0.3f, 0.2f), Point.of(0.5f, 0.6f));
        Outline outline2 = Outline.getRectangleBy2Point(Point.of(0.35f, 0.25f), Point.of(0.4f, 0.5f));
        Outline outline3 = Outline.getRectangleBy2Point(Point.of(0.5f, 0.6f), Point.of(0.7f, 0.7f));

        // then
        assertThat(universe.createSpaceInside(defaultSpaceID(), null, defaultSpaceMetadata(), defaultSpaceCommonMetadata(), outline1).overlapEvent().isOverlapped()).isFalse();
        assertThat(universe.createSpaceInside(defaultSpaceID(), null, defaultSpaceMetadata(), defaultSpaceCommonMetadata(), outline2).overlapEvent().isOverlapped()).isTrue();
        assertThat(universe.createPieceInside(defaultPieceID(), null, defaultPieceMetadata(), defaultPieceCommonMetadata(), outline1).overlapEvent().isOverlapped()).isTrue();
        assertThat(universe.createPieceInside(defaultPieceID(), null, defaultPieceMetadata(), defaultPieceCommonMetadata(), outline2).overlapEvent().isOverlapped()).isTrue();
        assertThat(universe.createPieceInside(defaultPieceID(), null, defaultPieceMetadata(), defaultPieceCommonMetadata(), outline3).overlapEvent().isOverlapped()).isFalse();
    }

    @Test
    @DisplayName("스페이스 생성")
    void createSpace() {
        // given
        Universe universe = defaultUniverseOnly().build();

        // when
        SpaceCreateEvent result = universe.createSpaceInside(defaultSpaceID(), null, defaultSpaceMetadata(), defaultSpaceCommonMetadata(), SpaceTestData.defaultOutline());

        // then
        assertThat(result.newSpace()).isNotNull();
        assertThat(universe.getSpaces()).hasSize(1);
    }

    @Test
    @DisplayName("피스 생성")
    void createPiece() {
        // given
        Universe universe = defaultUniverseOnly().build();

        // when
        PieceCreateEvent result = universe.createPieceInside(defaultPieceID(), null, defaultPieceMetadata(), defaultSpaceCommonMetadata(), SpaceTestData.defaultOutline());

        // then
        assertThat(result.newPiece()).isNotNull();
        assertThat(universe.getPieces()).hasSize(1);
    }

    @Test
    @DisplayName("스페이스 및 피스 생성 위임")
    void delegateCreateSpaceAndPiece() {
        // given
        Universe universe = defaultUniverse();
        SpaceID parentSpaceID = universe.getSpaces().getFirst().getId();

        // when
        SpaceCreateEvent result1 = universe.createSpaceInside(defaultSpaceID(), parentSpaceID, defaultSpaceMetadata(), defaultSpaceCommonMetadata(), SpaceTestData.defaultOutline());
        PieceCreateEvent result2 = universe.createPieceInside(defaultPieceID(), parentSpaceID, defaultPieceMetadata(), defaultSpaceCommonMetadata(), PieceTestData.defaultOutline());

        // then
        assertThat(result1.newSpace()).isNull();
        assertThat(result2.newPiece()).isNotNull();
        assertThat(universe.getSpaces()).hasSize(2);
        assertThat(universe.getPieces()).hasSize(1);
        assertThat(universe.getSpaces().getFirst().getSpaces()).hasSize(1);
        assertThat(universe.getSpaces().getFirst().getPieces()).hasSize(2);
    }

    @Test
    @DisplayName("유니버스 상세정보 수정")
    void updateUniverseMetadata() {
        // given
        Universe universe = defaultUniverseOnly().build();

        Category category = new Category(UuidCreator.getTimeOrderedEpoch(), "category2", "카테고리2");
        Owner owner = new Owner(UuidCreator.getTimeOrderedEpoch(), "leaffael");
        String title = "수정";
        String description = "수정된 내용";
        AccessLevel accessLevel = AccessLevel.PRIVATE;
        List<String> tags = List.of("수", "정");

        // when
        UniverseMetadataUpdateEvent event = universe.updateMetadata(category, owner, title, description, accessLevel, tags);

        // then
        assertThat(event.universeID()).isEqualTo(universe.getId());
        assertThat(event.category()).isEqualTo(category);
        assertThat(event.owner()).isEqualTo(owner);
        assertThat(event.title()).isEqualTo(title);
        assertThat(event.description()).isEqualTo(description);
        assertThat(event.accessLevel()).isEqualTo(accessLevel);
        assertThat(event.tags()).isEqualTo(tags);
        assertThat(event.updatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));

        assertThat(universe.getCommonMetadata().getTitle()).isEqualTo(title);
        assertThat(universe.getCommonMetadata().getDescription()).isEqualTo(description);
        assertThat(universe.getUniverseMetadata().getAccessLevel()).isEqualTo(accessLevel);
        assertThat(universe.getUniverseMetadata().getTags()).isEqualTo(tags);
        assertThat(universe.getCommonMetadata().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));
    }

    @Test
    @DisplayName("유니버스 파일 덮어쓰기")
    void overwriteUniverseFile() {
        // given
        Universe universe = defaultUniverseOnly().build();
        UUID oldThumbmusicID = universe.getUniverseMetadata().getThumbmusicID();
        UUID oldThumbnailID = universe.getUniverseMetadata().getThumbnailID();
        UUID oldInnerImageID = universe.getUniverseMetadata().getBackgroundID();

        UUID newThumbmusicID = UuidCreator.getTimeOrderedEpoch();
        UUID newThumbnailID = UuidCreator.getTimeOrderedEpoch();
        UUID newInnerImageID = UuidCreator.getTimeOrderedEpoch();

        // when
        UniverseFileOverwriteEvent event = universe.overwriteFiles(newThumbmusicID, newThumbnailID, newInnerImageID);

        // then
        assertThat(event.oldThumbmusicID()).isEqualTo(oldThumbmusicID);
        assertThat(event.oldThumbnailID()).isEqualTo(oldThumbnailID);
        assertThat(event.oldBackgroundID()).isEqualTo(oldInnerImageID);
        assertThat(event.newThumbmusicID()).isEqualTo(newThumbmusicID);
        assertThat(event.newThumbnailID()).isEqualTo(newThumbnailID);
        assertThat(event.newBackgroundID()).isEqualTo(newInnerImageID);

        assertThat(universe.getUniverseMetadata().getThumbmusicID()).isEqualTo(newThumbmusicID);
        assertThat(universe.getUniverseMetadata().getThumbnailID()).isEqualTo(newThumbnailID);
        assertThat(universe.getUniverseMetadata().getBackgroundID()).isEqualTo(newInnerImageID);

        oldThumbmusicID = newThumbmusicID;
        oldThumbnailID = newThumbnailID;
        oldInnerImageID = newInnerImageID;
        newThumbmusicID = UuidCreator.getTimeOrderedEpoch();
        newThumbnailID = UuidCreator.getTimeOrderedEpoch();
        newInnerImageID = UuidCreator.getTimeOrderedEpoch();

        UniverseFileOverwriteEvent changeThumbmusic = universe.overwriteFiles(newThumbmusicID, null, null);

        assertThat(changeThumbmusic.oldThumbmusicID()).isEqualTo(oldThumbmusicID);
        assertThat(changeThumbmusic.newThumbmusicID()).isEqualTo(newThumbmusicID);
        assertThat(changeThumbmusic.oldThumbnailID()).isEqualTo(changeThumbmusic.newThumbnailID()).isNull();
        assertThat(changeThumbmusic.oldBackgroundID()).isEqualTo(changeThumbmusic.newBackgroundID()).isNull();

        UniverseFileOverwriteEvent changeThumbnail = universe.overwriteFiles(null, newThumbnailID, null);

        assertThat(changeThumbnail.oldThumbnailID()).isEqualTo(oldThumbnailID);
        assertThat(changeThumbnail.newThumbnailID()).isEqualTo(newThumbnailID);
        assertThat(changeThumbnail.oldThumbmusicID()).isEqualTo(changeThumbnail.newThumbmusicID()).isNull();
        assertThat(changeThumbnail.oldBackgroundID()).isEqualTo(changeThumbnail.newBackgroundID()).isNull();

        UniverseFileOverwriteEvent changeInnerImage = universe.overwriteFiles(null, null, newInnerImageID);

        assertThat(changeInnerImage.oldBackgroundID()).isEqualTo(oldInnerImageID);
        assertThat(changeInnerImage.newBackgroundID()).isEqualTo(newInnerImageID);
        assertThat(changeInnerImage.oldThumbmusicID()).isEqualTo(changeInnerImage.newThumbmusicID()).isNull();
        assertThat(changeInnerImage.oldThumbnailID()).isEqualTo(changeInnerImage.newThumbnailID()).isNull();
    }

    @Test
    @DisplayName("유니버스 삭제")
    void delete() {
        // given
        Universe universe = defaultUniverse();

        // when
        UniverseDeleteEvent event = universe.delete();

        // then
        assertThat(event.deleteUniverseID()).isEqualTo(universe.getId());
        assertThat(event.deleteFileIDs()).hasSize(19);
        assertThat(event.deleteSpaceIDs()).hasSize(5);
        assertThat(event.deletePieceIDs()).hasSize(7);
        assertThat(event.deleteSoundIDs()).hasSize(11);
    }
}