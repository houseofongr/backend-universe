package com.hoo.aar.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.UniverseMapper;
import com.hoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Sql("classpath:sql/universe.sql")
@Import({UniversePersistenceAdapter.class, UniverseMapper.class})
class UniversePersistenceAdapterTest {

    @Autowired
    UniversePersistenceAdapter sut;

    @Test
    @DisplayName("유니버스 전체 조회")
    void testSearchPageSize() {
        // given
        SearchPublicUniverseCommand command10 = new SearchPublicUniverseCommand(PageRequest.of(0, 10), null, null, null, null, false);

        // when
        SearchPublicUniverseResult resultSize10 = sut.searchPublicUniverse(command10);

        // then
        assertThat(resultSize10.universes().size()).isEqualTo(8);

        // 페이지네이션 개수 확인
        assertThat(resultSize10.pagination().size()).isEqualTo(10);

        // 토탈 페이지 개수 확인
        assertThat(resultSize10.pagination().totalPages()).isEqualTo(1);

        // 카운트 쿼리 확인
        assertThat(resultSize10.pagination().totalElements()).isEqualTo(8);
    }


    @Test
    @DisplayName("공개 유니버스 중 특정 컨텐츠(이름, 내용, 작성자)가 포함된 유니버스 조회")
    void testSearchKeyword() {
        // given
        SearchPublicUniverseCommand content_건강 = new SearchPublicUniverseCommand(PageRequest.of(0, 10), 1L, "content", "건강", null, false);
        SearchPublicUniverseCommand content_콘텐츠 = new SearchPublicUniverseCommand(PageRequest.of(0, 10), 1L, "content", "콘텐츠", null, false);
        SearchPublicUniverseCommand author_leaf = new SearchPublicUniverseCommand(PageRequest.of(0, 10), 1L, "author", "leaf", null, false);
        SearchPublicUniverseCommand all_건 = new SearchPublicUniverseCommand(PageRequest.of(0, 10), 1L, "all", "건", null, false);

        // when
        SearchPublicUniverseResult result_content_건강 = sut.searchPublicUniverse(content_건강);
        SearchPublicUniverseResult result_content_콘텐츠 = sut.searchPublicUniverse(content_콘텐츠);
        SearchPublicUniverseResult result_author_leaf = sut.searchPublicUniverse(author_leaf);
        SearchPublicUniverseResult result_all_건 = sut.searchPublicUniverse(all_건);

        // then
        assertThat(result_content_건강.universes().size()).isEqualTo(2);
        assertThat(result_content_콘텐츠.universes().size()).isEqualTo(2);
        assertThat(result_author_leaf.universes().size()).isEqualTo(5);
        assertThat(result_all_건.universes().size()).isEqualTo(2);
        assertThat(result_content_건강.universes()).allMatch(universeInfo -> universeInfo.title().contains("건강") || universeInfo.description().contains("건강"));
        assertThat(result_content_콘텐츠.universes()).allMatch(universeInfo -> universeInfo.title().contains("콘텐츠") || universeInfo.description().contains("콘텐츠"));
        assertThat(result_author_leaf.universes()).allMatch(universeInfo -> universeInfo.author().contains("leaf"));
        assertThat(result_all_건.universes()).allMatch(universeInfo -> universeInfo.title().contains("건") || universeInfo.description().contains("건") || universeInfo.author().contains("건"));
    }

    @Test
    @DisplayName("리스트 조회 시 본인이 좋아요 누른 컨텐츠 확인")
    void testIsLikedList() {
        // given
        SearchPublicUniverseCommand user1 = new SearchPublicUniverseCommand(PageRequest.of(0, 10), 1L, null, null, null, false);

        // when
        SearchPublicUniverseResult result_user1 = sut.searchPublicUniverse(user1);

        // then
        assertThat(result_user1.universes()).anySatisfy(
                universeListInfo -> {
                    assertThat(universeListInfo.id()).isEqualTo(10);
                    assertThat(universeListInfo.isLiked()).isTrue();
                }).anySatisfy(
                universeListInfo -> {
                    assertThat(universeListInfo.id()).isEqualTo(9);
                    assertThat(universeListInfo.isLiked()).isFalse();
                });
    }

    @Test
    @DisplayName("좋아요 표시한 유니버스인지 확인")
    void testCheckIsLiked() {
        assertThat(sut.checkIsLiked(10L, 1L)).isTrue();
        assertThat(sut.checkIsLiked(9L, 1L)).isFalse();
    }

    @Test
    @DisplayName("유니버스 트리 조회")
    void testFindUniverseTree() {
        // given
        Long universeId = 1L;

        // when
        TraversalComponents treeComponents = sut.viewPublicUniverse(universeId);

        // then
        assertThat(treeComponents.getUniverse().getId()).isEqualTo(1L);
        assertThat(treeComponents.getUniverse().getFileInfo().getImageId()).isEqualTo(3L);
        assertThat(treeComponents.getSpaces()).hasSize(4);
        assertThat(treeComponents.getPieces()).hasSize(6);
        assertThat(treeComponents.getUniverse().getSocialInfo().getViewCount()).isEqualTo(2L);
    }
}