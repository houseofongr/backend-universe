package com.hoo.universe.adapter.out.persistence.query;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.universe.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.universe.api.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.dto.query.OpenPieceQueryResult;
import com.hoo.universe.api.dto.query.UniverseListQueryInfo;
import com.hoo.universe.api.dto.result.UniverseListInfo;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class UniverseQueryAdapterTest {

    @Autowired
    UniverseQueryAdapter sut;
    @Autowired
    private PieceJpaRepository pieceJpaRepository;

    @Test
    @DisplayName("카테고리 검색 테스트")
    void testFindAllCategory() {

        // when
        List<Category> result = sut.findAllCategories();

        // then
        assertThat(result)
                .anyMatch(category -> category.getEng().equalsIgnoreCase("Life"))
                .anyMatch(category -> category.getEng().equalsIgnoreCase("Public"))
                .anyMatch(category -> category.getEng().equalsIgnoreCase("Government"));
    }

    @Test
    @DisplayName("유니버스 10, 20, 50건 조회")
    void testSearchPageSize() {
        // given
        SearchUniverseCommand command10 = new SearchUniverseCommand(PageRequest.of(1, 10), null);
        SearchUniverseCommand command20 = new SearchUniverseCommand(PageRequest.of(1, 20), null);
        SearchUniverseCommand command50 = new SearchUniverseCommand(PageRequest.of(1, 50), null);

        // when
        PageQueryResult<UniverseListQueryInfo> resultSize10 = sut.searchUniverses(command10);
        PageQueryResult<UniverseListQueryInfo> resultSize20 = sut.searchUniverses(command20);
        PageQueryResult<UniverseListQueryInfo> resultSize50 = sut.searchUniverses(command50);

        // then
        assertThat(resultSize10.content().size()).isEqualTo(10);
        assertThat(resultSize20.content().size()).isEqualTo(12);
        assertThat(resultSize50.content().size()).isEqualTo(12);

        // 페이지네이션 개수 확인
        assertThat(resultSize10.pagination().size()).isEqualTo(10);
        assertThat(resultSize20.pagination().size()).isEqualTo(20);
        assertThat(resultSize50.pagination().size()).isEqualTo(50);

        // 토탈 페이지 개수 확인
        assertThat(resultSize10.pagination().totalPages()).isEqualTo(2);
        assertThat(resultSize20.pagination().totalPages()).isEqualTo(1);
        assertThat(resultSize50.pagination().totalPages()).isEqualTo(1);

        // 카운트 쿼리 확인
        assertThat(resultSize10.pagination().totalElements()).isEqualTo(12);
        assertThat(resultSize20.pagination().totalElements()).isEqualTo(12);
        assertThat(resultSize50.pagination().totalElements()).isEqualTo(12);
    }

    @Test
    @DisplayName("특정 컨텐츠(이름, 내용, 작성자)가 포함된 유니버스 조회")
    void testSearchKeyword() {
        // given
        SearchUniverseCommand content_건강 = new SearchUniverseCommand(PageRequest.ofWithKeyword("content", "건강"), null);
        SearchUniverseCommand content_콘텐츠 = new SearchUniverseCommand(PageRequest.ofWithKeyword("content", "콘텐츠"), null);
        SearchUniverseCommand owner_leaf = new SearchUniverseCommand(PageRequest.ofWithKeyword("owner", "leaf"), null);
        SearchUniverseCommand all_유니버스 = new SearchUniverseCommand(PageRequest.ofWithKeyword("all", "유니버스"), null);

        // when
        PageQueryResult<UniverseListQueryInfo> result_content_건강 = sut.searchUniverses(content_건강);
        PageQueryResult<UniverseListQueryInfo> result_content_콘텐츠 = sut.searchUniverses(content_콘텐츠);
        PageQueryResult<UniverseListQueryInfo> result_owner_leaf = sut.searchUniverses(owner_leaf);
        PageQueryResult<UniverseListQueryInfo> result_all_유니버스 = sut.searchUniverses(all_유니버스);

        // then
        assertThat(result_content_건강.content().size()).isEqualTo(3);
        assertThat(result_content_콘텐츠.content().size()).isEqualTo(4);
        assertThat(result_owner_leaf.content().size()).isEqualTo(10);
        assertThat(result_all_유니버스.content().size()).isEqualTo(9);
        assertThat(result_content_건강.content()).allMatch(universeInfo -> universeInfo.title().contains("건강") || universeInfo.description().contains("건강"));
        assertThat(result_content_콘텐츠.content()).allMatch(universeInfo -> universeInfo.title().contains("콘텐츠") || universeInfo.description().contains("콘텐츠"));
        assertThat(result_owner_leaf.content()).allMatch(universeInfo -> universeInfo.owner().contains("leaf"));
        assertThat(result_all_유니버스.content()).allMatch(universeInfo -> universeInfo.title().contains("유니버스") || universeInfo.description().contains("유니버스") || universeInfo.owner().contains("유니버스"));
    }

    @Test
    @DisplayName("특정 카테고리가 포함된 유니버스 조회")
    void testFilterCategory() {
        // given
        UUID categoryID = UUID.fromString("019809b6-2061-7538-84a3-d2d9943521cc");
        UUID categoryID2 = UUID.fromString("019809b6-2061-7c79-8a77-188f3cad10cf");
        SearchUniverseCommand LIFE = new SearchUniverseCommand(PageRequest.defaultPage(), categoryID);
        SearchUniverseCommand PUBLIC = new SearchUniverseCommand(PageRequest.defaultPage(), categoryID2);

        // when
        PageQueryResult<UniverseListQueryInfo> result_LIFE = sut.searchUniverses(LIFE);
        PageQueryResult<UniverseListQueryInfo> result_PUBLIC = sut.searchUniverses(PUBLIC);

        // then
        assertThat(result_LIFE.content().size()).isEqualTo(8);
        assertThat(result_PUBLIC.content().size()).isEqualTo(3);
        assertThat(result_LIFE.content()).allMatch(universeInfo -> universeInfo.category().getEng().equals("LIFE"));
        assertThat(result_PUBLIC.content()).allMatch(universeInfo -> universeInfo.category().getEng().equals("PUBLIC"));
    }

    @Test
    @DisplayName("피스 검색")
    void searchPiece() {
        // given
        UUID pieceID = pieceJpaRepository.findUuidById(4L);
        PageRequest pageRequest = PageRequest.defaultPage();

        // when
        OpenPieceQueryResult result = sut.searchPiece(pieceID, pageRequest);

        // then
        assertThat(result.sounds().content()).hasSize(4);
    }
}