package com.hoo.universe.adapter.out.persistence;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.adapter.out.persistence.entity.*;
import com.hoo.universe.adapter.out.persistence.repository.*;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.*;
import com.hoo.universe.domain.vo.*;
import com.hoo.universe.test.domain.PieceTestData;
import com.hoo.universe.test.domain.SpaceTestData;
import com.hoo.universe.test.domain.UniverseTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class JpaCommandAdapterTest {

    @Autowired
    JpaCommandAdapter sut;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    SpaceJpaRepository spaceJpaRepository;

    @Autowired
    SoundJpaRepository soundJpaRepository;

    @Autowired
    PieceJpaRepository pieceJpaRepository;

    @Autowired
    LoadUniversePort loadUniversePort;

    @Autowired
    private PersistenceMapper persistenceMapper;

    @Test
    @DisplayName("유니버스 생성 이벤트 처리")
    void handleUniverseCreateEvent() {
        // given
        Category firstCategory = persistenceMapper.mapToUniverseCategory(categoryJpaRepository.findAll().getFirst());
        Universe universe = defaultUniverseOnly()
                .withCategory(firstCategory)
                .withUniverseMetadata(
                        UniverseMetadata.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), AccessLevel.PUBLIC,
                                List.of("유", "니", "버스"))
                )
                .build();

        // when
        sut.saveUniverse(universe);
        UniverseJpaEntity newUniverseJpeEntity = universeJpaRepository.findByUuid(universe.getId().uuid()).orElseThrow();

        // then
        assertThat(newUniverseJpeEntity.getUuid()).isEqualTo(universe.getId().uuid());
        assertThat(newUniverseJpeEntity.getUniverseHashtags()).hasSize(3);
    }

    @Test
    @DisplayName("스페이스 생성 이벤트 처리")
    void handleSpaceCreateEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        SpaceCreateEvent event = universe.createSpaceInside(
                new Space.SpaceID(UUID.randomUUID()),
                null,
                SpaceMetadata.create(
                        UUID.randomUUID(),
                        false),
                CommonMetadata.create("공간", "스페이스는 공간입니다."),
                Outline.getRectangleBy2Point(0.9, 0.9, 0.95, 0.95)
        );

        // when
        sut.saveSpace(event.newSpace());
        SpaceJpaEntity spaceJpaEntity = findSpaceJpaEntity(event.newSpace());

        // then
        assertThat(spaceJpaEntity.getUuid()).isEqualTo(event.newSpace().getId().uuid());
    }

    @Test
    @DisplayName("피스 생성 이벤트 처리")
    void handlePieceCreateEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        PieceCreateEvent event = universe.createPieceInside(
                new Piece.PieceID(UUID.randomUUID()),
                null,
                PieceMetadata.create(
                        UUID.randomUUID(),
                        false),
                CommonMetadata.create("조각", "피스는 조각입니다."),
                Outline.getRectangleBy2Point(0.9, 0.9, 0.95, 0.95));

        // when
        sut.savePiece(event.newPiece());
        PieceJpaEntity pieceJpaEntity = findPieceJpaEntity(event.newPiece());

        // then
        assertThat(pieceJpaEntity.getUuid()).isEqualTo(event.newPiece().getId().uuid());
    }

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
        sut.saveSound(event.newSound());
        SoundJpaEntity soundJpaEntity = findSoundJpaEntity(event.newSound());

        // then
        assertThat(soundJpaEntity.getUuid()).isEqualTo(event.newSound().getId().uuid());
    }

    @Test
    @DisplayName("카테고리 생성")
    void createCategory() {
        // given
        UUID newUuid = UuidCreator.getTimeOrderedEpoch();
        String kor = "새 카테고리";
        String eng = "new category";

        // when
        sut.saveCategory(newUuid, eng, kor);

        // then
        assertThat(categoryJpaRepository.findByUuid(newUuid)).isPresent();
    }

    @Test
    @DisplayName("유니버스 상세정보 수정 이벤트 처리")
    void handleUniverseMetadataUpdateEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        String title = "수정";

        UniverseMetadataUpdateEvent event = universe.updateMetadata(null, null, title, null, null, null);

        // when
        sut.updateUniverseMetadata(event);
        UniverseJpaEntity newUniverseJpeEntity = findUniverseJpaEntity(universe);

        // then
        assertThat(newUniverseJpeEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
    }

    @Test
    @DisplayName("스페이스 상세정보 수정 이벤트 처리")
    void handleSpaceMetadataUpdateEvent() {
        // given
        Space space = getSpaceBuilder(1L).build();
        SpaceMetadataUpdateEvent event = space.updateMetadata("수정", null, true);

        // when
        sut.updateSpaceMetadata(event);
        SpaceJpaEntity spaceJpaEntity = findSpaceJpaEntity(space);

        // then
        assertThat(spaceJpaEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
        assertThat(spaceJpaEntity.getCommonMetadata().getDescription()).isEqualTo("스페이스는 공간입니다.");
        assertThat(spaceJpaEntity.getSpaceMetadata().getHidden()).isTrue();
    }

    @Test
    @DisplayName("피스 상세정보 수정 이벤트 처리")
    void handlePieceMetadataUpdateEvent() {
        // given
        Piece piece = getPieceBuilder(1L).build();
        PieceMetadataUpdateEvent event = piece.updateMetadata("수정", null, true);

        // when
        sut.updatePieceMetadata(event);
        PieceJpaEntity pieceJpaEntity = findPieceJpaEntity(piece);

        // then
        assertThat(pieceJpaEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
        assertThat(pieceJpaEntity.getCommonMetadata().getDescription()).isEqualTo("피스는 조각입니다.");
        assertThat(pieceJpaEntity.getPieceMetadata().getHidden()).isTrue();
    }

    @Test
    @DisplayName("사운드 상세정보 수정 이벤트 처리")
    void handleSoundMetadataUpdateEvent() {
        // given
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeJpaRepository.findUuidById(1L));
        Sound sound = universe.getSpaces().getLast().getSpaces().getFirst().getPieces().getFirst().getSounds().getFirst();
        SoundMetadataUpdateEvent event = sound.updateMetadata("수정", null, true);

        // when
        sut.updateSoundMetadata(event);
        SoundJpaEntity soundJpaEntity = findSoundJpaEntity(sound);

        // then
        assertThat(soundJpaEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
        assertThat(soundJpaEntity.getCommonMetadata().getDescription()).isEqualTo("잔잔한 시냇물 소리를 담았습니다.");
        assertThat(soundJpaEntity.getSoundMetadata().getHidden()).isTrue();
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() {
        // given
        UUID id = UUID.fromString("019809b6-2061-7538-84a3-d2d9943521cc");
        String kor = "수정된 카테고리";
        String eng = "altered category";

        // when
        sut.updateCategory(id, eng, kor);
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findByUuid(id).orElseThrow();

        // then
        assertThat(categoryJpaEntity.getTitleKor()).isEqualTo(kor);
        assertThat(categoryJpaEntity.getTitleEng()).isEqualTo(eng);
    }

    @Test
    @DisplayName("유니버스 파일 덮어쓰기 이벤트 처리")
    void handleUniverseFileOverwriteEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        UUID newInnerImageID = UUID.randomUUID();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, null, newInnerImageID);

        // when
        sut.updateUniverseFileOverwrite(event);
        UniverseJpaEntity newUniverseJpeEntity = findUniverseJpaEntity(universe);

        // then
        assertThat(newUniverseJpeEntity.getUniverseMetadata().getBackgroundFileID()).isEqualTo(newInnerImageID);
        assertThat(newUniverseJpeEntity.getUniverseMetadata().getThumbmusicFileID().toString()).isEqualTo("11111111-0000-0000-0000-000000000001");
        assertThat(newUniverseJpeEntity.getUniverseMetadata().getThumbnailFileID().toString()).isEqualTo("22222222-0000-0000-0000-000000000001");
    }

    @Test
    @DisplayName("스페이스 파일 덮어쓰기 이벤트 처리")
    void handleSpaceFileOverwriteEvent() {
        // given
        Space space = getSpaceBuilder(1L).build();
        UUID newInnerImageID = UUID.randomUUID();
        SpaceFileOverwriteEvent event = space.overwriteFile(newInnerImageID);

        // when
        sut.updateSpaceFileOverwrite(event);
        SpaceJpaEntity spaceJpaEntity = findSpaceJpaEntity(space);

        // then
        assertThat(spaceJpaEntity.getSpaceMetadata().getBackgroundFileID()).isEqualTo(newInnerImageID);
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
        sut.updateSoundFileOverwrite(event);
        SoundJpaEntity soundJpaEntity = findSoundJpaEntity(sound);

        // then
        assertThat(soundJpaEntity.getSoundMetadata().getAudioFileID()).isEqualTo(newAudioID);
    }

    private UniverseJpaEntity findUniverseJpaEntity(Universe universe) {
        return universeJpaRepository.findByUuid(universe.getId().uuid()).orElseThrow();
    }

    @Test
    @DisplayName("스페이스 이동 이벤트 처리")
    void handleSpaceMoveEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(uuid);
        Outline rectangle = Outline.getRectangleBy2Point(0.05, 0.05, 0.1, 0.1);
        SpaceMoveEvent event = universe.moveSpace(universe.getSpaces().getLast().getSpaces().getFirst().getId(), rectangle);

        // when
        sut.updateSpaceMove(event);
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid()).orElseThrow();

        // then
        assertThat(spaceJpaEntity.getOutlinePoints()).isEqualTo("[[0.05,0.05],[0.05,0.1],[0.1,0.1],[0.1,0.05]]");
    }

    @Test
    @DisplayName("피스 이동 이벤트 처리")
    void handlePieceMoveEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(uuid);
        Outline rectangle = Outline.getRectangleBy2Point(0.05, 0.05, 0.1, 0.1);
        PieceMoveEvent event = universe.movePiece(universe.getPieces().getFirst().getId(), rectangle);

        // when
        sut.updatePieceMove(event);
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(event.pieceID().uuid()).orElseThrow();

        // then
        assertThat(pieceJpaEntity.getOutlinePoints()).isEqualTo("[[0.05,0.05],[0.05,0.1],[0.1,0.1],[0.1,0.05]]");
    }

    @Test
    @DisplayName("유니버스 삭제 이벤트 처리")
    void handleUniverseDeleteEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        UniverseDeleteEvent event = universe.delete();

        // when
        sut.deleteUniverse(event);

        // then
        assertThat(universeJpaRepository.findByUuid(universe.getId().uuid())).isEmpty();
    }

    @Test
    @DisplayName("스페이스 삭제 이벤트 처리")
    void handleSpaceDeleteEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(uuid);
        Space space = universe.getSpaces().getLast();
        SpaceDeleteEvent event = space.delete();

        // when
        sut.deleteSpace(event);

        // then
        assertThat(spaceJpaRepository.findByUuid(space.getId().uuid())).isEmpty();
    }

    @Test
    @DisplayName("피스 삭제 이벤트 처리")
    void handlePieceDeleteEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(uuid);
        Piece piece = universe.getSpaces().getLast().getSpaces().getFirst().getPieces().getFirst();
        PieceDeleteEvent event = piece.delete();
        System.out.println("event = " + event);

        // when
        sut.deletePiece(event);

        // then
        assertThat(pieceJpaRepository.findByUuid(piece.getId().uuid())).isEmpty();
    }

    @Test
    @DisplayName("사운드 삭제 이벤트 처리")
    void handleSoundDeleteEvent() {
        // given
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeJpaRepository.findUuidById(1L));
        Sound sound = universe.getSpaces().getLast().getSpaces().getFirst().getPieces().getFirst().getSounds().getFirst();
        SoundDeleteEvent event = sound.delete();

        // when
        sut.deleteSound(event);

        // then
        assertThat(soundJpaRepository.findByUuid(sound.getId().uuid())).isEmpty();
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() {
        // given
        UUID id = UuidCreator.getTimeOrderedEpoch();

        // when
        sut.deleteCategory(id);

        // then
        assertThat(categoryJpaRepository.findByUuid(id)).isEmpty();
    }

    private UniverseTestData.UniverseBuilder getUniverseBuilder(Long sqlID) {
        UUID universeID = universeJpaRepository.findUuidById(sqlID);
        return defaultUniverseOnly().withUniverseID(new Universe.UniverseID(universeID));
    }

    private SpaceTestData.SpaceBuilder getSpaceBuilder(Long sqlID) {
        UUID spaceID = spaceJpaRepository.findUuidById(sqlID);
        return SpaceTestData.defaultSpace().withSpaceID(new Space.SpaceID(spaceID));
    }

    private PieceTestData.PieceBuilder getPieceBuilder(Long sqlID) {
        UUID pieceID = pieceJpaRepository.findUuidById(sqlID);
        return PieceTestData.defaultPiece().withPieceID(new Piece.PieceID(pieceID));
    }

    private SpaceJpaEntity findSpaceJpaEntity(Space space) {
        return spaceJpaRepository.findByUuid(space.getId().uuid()).orElseThrow();
    }

    private PieceJpaEntity findPieceJpaEntity(Piece piece) {
        return pieceJpaRepository.findByUuid((piece.getId().uuid())).orElseThrow();
    }

    private SoundJpaEntity findSoundJpaEntity(Sound sound) {
        return soundJpaRepository.findByUuid(sound.getId().uuid()).orElseThrow();
    }
}