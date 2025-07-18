package com.hoo.admin.domain.room;

import com.hoo.admin.domain.house.room.RoomDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HouseDetailTest {

    @Test
    @DisplayName("룸 아이디 수정 테스트")
    void testUpdateInfo() {
        // given
        RoomDetail roomDetail = new RoomDetail("거실");
        RoomDetail roomDetail2 = new RoomDetail("거실");
        RoomDetail roomDetail3 = new RoomDetail("거실");

        // when
        roomDetail.update("현관");
        roomDetail2.update("");
        roomDetail3.update(null);

        // then
        assertThat(roomDetail.getName()).isEqualTo("현관");
        assertThat(roomDetail2.getName()).isEqualTo("거실");
        assertThat(roomDetail3.getName()).isEqualTo("거실");
    }
}