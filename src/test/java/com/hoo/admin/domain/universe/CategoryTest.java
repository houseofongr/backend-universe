package com.hoo.admin.domain.universe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    @DisplayName("Category 포함여부 확인")
    void testContains() {
        // given
        String life = "LiFe";
        String fashion = "fashion and beauty";
        String fashion2 = "fashion_and_beauty";

        // then
        assertThat(Category.contains(life)).isTrue();
        assertThat(Category.contains(fashion)).isFalse();
        assertThat(Category.contains(fashion2)).isTrue();
    }

}