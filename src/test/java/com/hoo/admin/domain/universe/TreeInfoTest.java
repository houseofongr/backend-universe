package com.hoo.admin.domain.universe;

import com.hoo.admin.domain.universe.space.Space;
import com.hoo.admin.domain.universe.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TreeInfoTest {

    @Test
    @DisplayName("Universe를 Root로 하는 Tree 생성 테스트")
    void testCreateRoot() {

        // when
        TreeInfo root = MockTreeInfo.getTreeInfo();

        // then
        assertThat(root.getParent()).isNull();
        assertThat(root.getDepth()).isEqualTo(0);
        assertThat(root.getUniverseTreeComponent()).isInstanceOf(Universe.class);
        assertThat(root.getChildren()).hasSize(3);
    }

    @Test
    @DisplayName("DFS 탐색 테스트")
    void testDFS() {
        // given
        TreeInfo root = MockTreeInfo.getTreeInfo();

        // when
        TreeInfo space1 = root.getComponent(Space.class, 1L).getTreeInfo();
        TreeInfo space2 = root.getComponent(Space.class, 2L).getTreeInfo();
        TreeInfo space3 = root.getComponent(Space.class, 3L).getTreeInfo();
        TreeInfo space4 = root.getComponent(Space.class, 4L).getTreeInfo();
        TreeInfo space5 = root.getComponent(Space.class, 5L).getTreeInfo();
        TreeInfo element1 = root.getComponent(Piece.class, 1L).getTreeInfo();
        TreeInfo element2 = root.getComponent(Piece.class, 2L).getTreeInfo();
        TreeInfo element3 = root.getComponent(Piece.class, 3L).getTreeInfo();
        TreeInfo element4 = root.getComponent(Piece.class, 4L).getTreeInfo();
        TreeInfo element5 = root.getComponent(Piece.class, 5L).getTreeInfo();
        TreeInfo element6 = root.getComponent(Piece.class, 6L).getTreeInfo();
        TreeInfo element7 = root.getComponent(Piece.class, 7L).getTreeInfo();

        // then
        assertThat(root.getComponent(Space.class, 6L)).isNull();
        assertThat(root.getComponent(Piece.class, 8L)).isNull();

        assertDFS(space1, Space.class, 1, 1, root);
        assertDFS(space2, Space.class, 2, 1, root);
        assertDFS(space3, Space.class, 3, 2, space1);
        assertDFS(space4, Space.class, 4, 2, space2);
        assertDFS(space5, Space.class, 5, 2, space2);

        assertDFS(element1, Piece.class, 1, 1, root);
        assertDFS(element2, Piece.class, 2, 2, space1);
        assertDFS(element3, Piece.class, 3, 3, space3);
        assertDFS(element4, Piece.class, 4, 3, space4);
        assertDFS(element5, Piece.class, 5, 3, space4);
        assertDFS(element6, Piece.class, 6, 3, space5);
        assertDFS(element7, Piece.class, 7, 3, space5);
    }

    @Test
    @DisplayName("트리의 모든 요소 반환 테스트")
    void testGetAllComponents() {
        // given
        TreeInfo root = MockTreeInfo.getTreeInfo();

        // when
        TraversalComponents components = root.getAllComponents();

        // then
        assertThat(components.getUniverse()).isEqualTo(root.getUniverseTreeComponent());
        assertThat(components.getSpaces()).hasSize(5)
                .anyMatch(space -> space.getId().equals(1L))
                .anyMatch(space -> space.getId().equals(2L))
                .anyMatch(space -> space.getId().equals(3L))
                .anyMatch(space -> space.getId().equals(4L))
                .anyMatch(space -> space.getId().equals(5L));
        assertThat(components.getPieces()).hasSize(7)
                .anyMatch(piece -> piece.getId().equals(1L))
                .anyMatch(piece -> piece.getId().equals(2L))
                .anyMatch(piece -> piece.getId().equals(3L))
                .anyMatch(piece -> piece.getId().equals(4L))
                .anyMatch(piece -> piece.getId().equals(5L))
                .anyMatch(piece -> piece.getId().equals(6L))
                .anyMatch(piece -> piece.getId().equals(7L));
    }

    @Test
    @DisplayName("스페이스가 없이 존재하는 피스 테스트케이스")
    void pieceWithoutSpaceTestCase() {
        // given
        TreeInfo root = MockTreeInfo.getTreeInfo_pieceWithoutSpace();

        // when
        TraversalComponents components = root.getAllComponents();

        // then
        assertThat(components.getUniverse()).isEqualTo(root.getUniverseTreeComponent());
        assertThat(components.getSpaces()).hasSize(4)
                .anyMatch(space -> space.getId().equals(1L))
                .anyMatch(space -> space.getId().equals(2L))
                .anyMatch(space -> space.getId().equals(3L))
                .anyMatch(space -> space.getId().equals(4L));
        assertThat(components.getPieces()).hasSize(5)
                .anyMatch(piece -> piece.getId().equals(1L))
                .anyMatch(piece -> piece.getId().equals(2L))
                .anyMatch(piece -> piece.getId().equals(3L))
                .anyMatch(piece -> piece.getId().equals(4L))
                .anyMatch(piece -> piece.getId().equals(5L));
    }

    private void assertDFS(TreeInfo target, Class<?> clazz, long id, int depth, TreeInfo parent) {
        assertThat(target.getDepth()).isEqualTo(depth);
        assertThat(target.getUniverseTreeComponent()).isInstanceOf(clazz);
        assertThat(target.getUniverseTreeComponent().getId()).isEqualTo(id);
        assertThat(target.getParent()).isEqualTo(parent);
    }

}