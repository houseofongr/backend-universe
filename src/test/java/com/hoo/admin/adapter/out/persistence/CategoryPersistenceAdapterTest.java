package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.CategoryMapper;
import com.hoo.admin.application.port.in.category.*;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.CategoryJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Sql("classpath:sql/universe.sql")
@Import({CategoryPersistenceAdapter.class, CategoryMapper.class})
class CategoryPersistenceAdapterTest {

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    CategoryPersistenceAdapter sut;

    @Test
    @DisplayName("카테고리 생성 테스트")
    void testCreateCategory() {
        // given
        String kor = "새 카테고리";
        String eng = "new category";

        // when
        CreateCategoryResult result = sut.save(eng, kor);

        // then
        assertThat(result.categoryId()).isNotNull();
        assertThat(result.kor()).isEqualTo(kor);
        assertThat(result.eng()).isEqualTo(eng);
    }

    @Test
    @DisplayName("카테고리 검색 테스트")
    void testFindAllCategory() {

        // when
        SearchCategoryResult result = sut.findAll();

        // then
        assertThat(result.categories())
                .anyMatch(categoryInfo -> categoryInfo.id().equals(1L) && categoryInfo.eng().equalsIgnoreCase("Life"))
                .anyMatch(categoryInfo -> categoryInfo.id().equals(2L) && categoryInfo.eng().equalsIgnoreCase("Public"))
                .anyMatch(categoryInfo -> categoryInfo.id().equals(3L) && categoryInfo.eng().equalsIgnoreCase("Government"));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void testUpdateCategory() {
        // given
        Long id = 1L;
        String kor = "수정된 카테고리";
        String eng = "altered category";

        // when
        UpdateCategoryResult result = sut.update(id, eng, kor);
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(1L).orElseThrow();

        // then
        assertThat(result.categoryId()).isEqualTo(id);
        assertThat(result.kor()).isEqualTo(kor);
        assertThat(result.eng()).isEqualTo(eng);
        assertThat(categoryJpaEntity.getTitleKor()).isEqualTo(kor);
        assertThat(categoryJpaEntity.getTitleEng()).isEqualTo(eng);
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void testDeleteCategory() {
        // given
        Long id = 1L;

        // when
        DeleteCategoryResult result = sut.delete(id);

        // then
        assertThat(result.deletedCategoryId()).isEqualTo(id);
        assertThat(categoryJpaRepository.findById(id)).isEmpty();
    }
}