package com.hoo.admin.domain.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemDetailTest {

    @Test
    @DisplayName("이름 수정")
    void testUpdateName() {
        // given
        ItemDetail detail = new ItemDetail("원래 이름");
        String _null = null;
        String empty = " ";
        String name = "변경 이름";

        detail.updateName(_null);
        assertThat(detail.getName()).isEqualTo("원래 이름");

        detail.updateName(empty);
        assertThat(detail.getName()).isEqualTo("원래 이름");

        detail.updateName(name);
        assertThat(detail.getName()).isEqualTo("변경 이름");
    }

}