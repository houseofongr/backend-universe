package com.hoo.universe.domain;

import com.hoo.universe.domain.event.PieceMoveEvent;
import com.hoo.universe.domain.event.SpaceMoveEvent;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.assertj.core.api.Assertions.assertThat;

public class UniverseTreeTest {

    @Test
    @DisplayName("유니버스 전체 트리 불러오기")
    void loadUniverseTree() {
        // given
        Universe treeUniverse = defaultUniverse();

        // then
        assertThat(treeUniverse.getSpaces()).hasSize(2)
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스1"))
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스2"));
        assertThat(treeUniverse.getPieces()).hasSize(1)
                .anyMatch(piece -> piece.getCommonMetadata().getTitle().equals("피스1"));

        Space firstChild = treeUniverse.getSpaces().get(0);
        Space secondChild = treeUniverse.getSpaces().get(1);

        assertThat(firstChild.getSpaces()).hasSize(1)
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스3"));

        assertThat(firstChild.getPieces()).hasSize(1)
                .anyMatch(piece -> piece.getCommonMetadata().getTitle().equals("피스2"));

        assertThat(secondChild.getSpaces()).hasSize(2)
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스4"))
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스5"));

        assertThat(secondChild.getPieces()).isEmpty();
    }

    @Test
    @DisplayName("특정 스페이스 검색")
    void getSpace() {
        // given
        Universe treeUniverse = defaultUniverse();

        // when
        Space 스페이스2 = treeUniverse.getSpaces().getLast();
        Space 스페이스3 = treeUniverse.getSpaces().getFirst().getSpaces().getFirst();

        // then
        assertThat(treeUniverse.getSpace(스페이스2.getId())).isEqualTo(스페이스2);
        assertThat(treeUniverse.getSpace(스페이스3.getId())).isEqualTo(스페이스3);
    }

    @Test
    @DisplayName("특정 피스 검색")
    void getPiece() {
        // given
        Universe treeUniverse = defaultUniverse();

        // when
        Piece 피스2 = treeUniverse.getSpaces().getFirst().getPieces().getFirst();
        Piece 피스3 = treeUniverse.getSpaces().getFirst().getSpaces().getFirst().getPieces().getFirst();

        // then
        assertThat(treeUniverse.getPiece(피스2.getId())).isEqualTo(피스2);
        assertThat(treeUniverse.getPiece(피스3.getId())).isEqualTo(피스3);
    }

    @Test
    @DisplayName("스페이스 이동")
    void moveSpace() {
        // given
        Universe universe = defaultUniverse();
        Space 스페이스2 = universe.getSpaces().getLast();
        Outline newOutline = Outline.getRectangleBy2Point(Point.of(0.7f, 0.7f), Point.of(0.8f, 0.8f));
        Outline overlappedOutline = Outline.getRectangleBy2Point(Point.of(0.1f, 0.1f), Point.of(0.2f, 0.2f));

        // when
        SpaceMoveEvent event = universe.moveSpace(스페이스2.getId(), newOutline);
        SpaceMoveEvent event2 = universe.moveSpace(스페이스2.getId(), overlappedOutline);

        // then
        assertThat(event.overlapEvent().isOverlapped()).isFalse();
        assertThat(event.outline()).isEqualTo(newOutline);
        assertThat(스페이스2.getOutline()).isEqualTo(newOutline);

        assertThat(event2.overlapEvent().isOverlapped()).isTrue();
        assertThat(event2.outline()).isNull();
    }

    @Test
    @DisplayName("피스 이동")
    void movePiece() {
        // given
        Universe universe = defaultUniverse();
        Piece 피스2 = universe.getSpaces().getFirst().getPieces().getFirst();
        Outline newOutline = Outline.getRectangleBy2Point(Point.of(0.7f, 0.7f), Point.of(0.8f, 0.8f));
        Outline overlappedOutline = Outline.getRectangleBy2Point(Point.of(0.1f, 0.5f), Point.of(0.2f, 0.6f));

        // when
        PieceMoveEvent event = universe.movePiece(피스2.getId(), newOutline);
        PieceMoveEvent event2 = universe.movePiece(피스2.getId(), overlappedOutline);

        // then
        assertThat(event.overlapEvent().isOverlapped()).isFalse();
        assertThat(event.outline()).isEqualTo(newOutline);
        assertThat(피스2.getOutline()).isEqualTo(newOutline);

        assertThat(event2.overlapEvent().isOverlapped()).isTrue();
        assertThat(event2.outline()).isNull();
    }

    @Test
    @DisplayName("유니버스 내부 요소 전체 검색")
    void findAllSpacesAndPieces() {
        // given
        Universe universe = defaultUniverse();

        // when
        List<Space> allSpaces = universe.getAllSpaces();
        List<Piece> allPieces = universe.getAllPieces();

        // then
        assertThat(allSpaces).hasSize(5);
        assertThat(allPieces).hasSize(7);
    }

    @Test
    @DisplayName("유니버스 내부 요소 깊이 구하기")
    void findDepth() {
        // given
        Universe universe = defaultUniverse();
        Space SPACE_1 = universe.getSpaces().getFirst();
        Space SPACE_2 = universe.getSpaces().getLast();
        Space SPACE_3 = SPACE_1.getSpaces().getFirst();
        Space SPACE_4 = SPACE_2.getSpaces().getFirst();
        Space SPACE_5 = SPACE_2.getSpaces().getLast();
        Piece PIECE_1 = universe.getPieces().getFirst();
        Piece PIECE_2 = SPACE_1.getPieces().getFirst();
        Piece PIECE_3 = SPACE_3.getPieces().getFirst();
        Piece PIECE_4 = SPACE_4.getPieces().getFirst();
        Piece PIECE_5 = SPACE_4.getPieces().getLast();
        Piece PIECE_6 = SPACE_5.getPieces().getFirst();
        Piece PIECE_7 = SPACE_5.getPieces().getLast();

        // then
        assertThat(universe.getDepth(SPACE_1.getId())).isEqualTo(1);
        assertThat(universe.getDepth(SPACE_2.getId())).isEqualTo(1);
        assertThat(universe.getDepth(SPACE_3.getId())).isEqualTo(2);
        assertThat(universe.getDepth(SPACE_4.getId())).isEqualTo(2);
        assertThat(universe.getDepth(SPACE_5.getId())).isEqualTo(2);
        assertThat(universe.getDepth(PIECE_1.getId())).isEqualTo(1);
        assertThat(universe.getDepth(PIECE_2.getId())).isEqualTo(2);
        assertThat(universe.getDepth(PIECE_3.getId())).isEqualTo(3);
        assertThat(universe.getDepth(PIECE_4.getId())).isEqualTo(3);
        assertThat(universe.getDepth(PIECE_5.getId())).isEqualTo(3);
        assertThat(universe.getDepth(PIECE_6.getId())).isEqualTo(3);
        assertThat(universe.getDepth(PIECE_7.getId())).isEqualTo(3);
    }

}
