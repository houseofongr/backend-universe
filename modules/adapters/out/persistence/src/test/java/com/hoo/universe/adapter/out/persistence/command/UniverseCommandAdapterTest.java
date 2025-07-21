package com.hoo.universe.adapter.out.persistence.command;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.universe.adapter.out.persistence.UniverseMapper;
import com.hoo.universe.adapter.out.persistence.entity.CategoryJpaEntity;
import com.hoo.universe.adapter.out.persistence.repository.CategoryJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class UniverseCommandAdapterTest {

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    CommandCategoryAdapter sut;

    @Autowired
    UniverseJpaRepository universeJpaRepository;
    @Autowired
    private UniverseMapper universeMapper;

    @Test
    @DisplayName("카테고리 생성")
    void createCategory() {
        // given
        UUID newUuid = UuidCreator.getTimeOrderedEpoch();
        String kor = "새 카테고리";
        String eng = "new category";

        // when
        sut.saveNewCategory(newUuid, eng, kor);

        // then
        assertThat(categoryJpaRepository.findByUuid(newUuid)).isPresent();
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
    @DisplayName("카테고리 삭제")
    void deleteCategory() {
        // given
        UUID id = UuidCreator.getTimeOrderedEpoch();

        // when
        sut.deleteCategory(id);

        // then
        assertThat(categoryJpaRepository.findByUuid(id)).isEmpty();
    }
}