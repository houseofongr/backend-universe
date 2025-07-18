package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.universe.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.universe.adapter.out.persistence.query.UniverseLoadAdapter;
import com.hoo.universe.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.piece.PieceCreateEvent;
import com.hoo.universe.domain.event.piece.PieceDeleteEvent;
import com.hoo.universe.domain.event.piece.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.piece.PieceMoveEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;
import com.hoo.universe.domain.vo.Outline;
import com.hoo.universe.test.domain.PieceTestData;
import com.hoo.universe.test.domain.UniverseTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class PieceEventHandleAdapterTest {

    @Autowired
    PieceEventHandleAdapter sut;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    PieceJpaRepository pieceJpaRepository;

    @Autowired
    UniverseLoadAdapter universeLoadAdapter;

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
        sut.handlePieceCreateEvent(event);
        PieceJpaEntity pieceJpaEntity = findPieceJpaEntity(event.newPiece());

        // then
        assertThat(pieceJpaEntity.getUuid()).isEqualTo(event.newPiece().getId().uuid());
    }

    @Test
    @DisplayName("피스 상세정보 수정 이벤트 처리")
    void handlePieceMetadataUpdateEvent() {
        // given
        Piece piece = getPieceBuilder(1L).build();
        PieceMetadataUpdateEvent event = piece.updateMetadata("수정", null, true);

        // when
        sut.handlePieceMetadataUpdateEvent(event);
        PieceJpaEntity pieceJpaEntity = findPieceJpaEntity(piece);

        // then
        assertThat(pieceJpaEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
        assertThat(pieceJpaEntity.getCommonMetadata().getDescription()).isEqualTo("피스는 조각입니다.");
        assertThat(pieceJpaEntity.getPieceMetadata().getHidden()).isTrue();
    }

    @Test
    @DisplayName("피스 이동 이벤트 처리")
    void handlePieceMoveEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = universeLoadAdapter.loadUniverseWithAllEntity(uuid);
        Outline rectangle = Outline.getRectangleBy2Point(0.05, 0.05, 0.1, 0.1);
        PieceMoveEvent event = universe.movePiece(universe.getPieces().getFirst().getId(), rectangle);

        // when
        sut.handlePieceMoveEvent(event);
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(event.pieceID().uuid()).orElseThrow();

        // then
        assertThat(pieceJpaEntity.getOutlinePoints()).isEqualTo("[[0.05,0.05],[0.05,0.1],[0.1,0.1],[0.1,0.05]]");
    }

    @Test
    @DisplayName("피스 삭제 이벤트 처리")
    void handlePieceDeleteEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = universeLoadAdapter.loadUniverseWithAllEntity(uuid);
        Piece piece = universe.getSpaces().getLast().getSpaces().getFirst().getPieces().getFirst();
        PieceDeleteEvent event = piece.delete();
        System.out.println("event = " + event);

        // when
        sut.handlePieceDeleteEvent(event);

        // then
        assertThat(pieceJpaRepository.findByUuid(piece.getId().uuid())).isEmpty();
    }


    private UniverseTestData.UniverseBuilder getUniverseBuilder(Long sqlID) {
        UUID universeID = universeJpaRepository.findUuidById(sqlID);
        return defaultUniverseOnly().withUniverseID(new Universe.UniverseID(universeID));
    }

    private PieceTestData.PieceBuilder getPieceBuilder(Long sqlID) {
        UUID pieceID = pieceJpaRepository.findUuidById(sqlID);
        return PieceTestData.defaultPiece().withPieceID(new Piece.PieceID(pieceID));
    }


    private PieceJpaEntity findPieceJpaEntity(Piece piece) {
        return pieceJpaRepository.findByUuid((piece.getId().uuid())).orElseThrow();
    }

}