package com.hoo.admin.domain.house;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HouseTest {

    @Test
    @DisplayName("업데이트 테스트")
    void testUpdateInfo() {
        // given
        HouseDetail houseDetail = new HouseDetail("cozy house", "leaf", "this is cozy house.");
        HouseDetail houseDetail2 = new HouseDetail("cozy house", "leaf", "this is cozy house.");

        String title = "not cozy house";
        String author = null;
        String description = "this is not cozy house.";

        // when
        houseDetail.update(title, author, description);
        houseDetail2.update(title, "", description);

        // then
        assertThat(houseDetail.getTitle()).isEqualTo(title);
        assertThat(houseDetail.getAuthor()).isEqualTo("leaf");
        assertThat(houseDetail.getDescription()).isEqualTo(description);
        assertThat(houseDetail2.getAuthor()).isEqualTo("");
    }

}