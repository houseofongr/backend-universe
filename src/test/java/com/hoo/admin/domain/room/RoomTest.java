package com.hoo.admin.domain.room;

import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.house.room.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomTest {

    @Test
    @DisplayName("룸 생성 테스트")
    void testCreateRoom() throws AxisLimitExceededException, AreaLimitExceededException {
        // when
        Room newRoom = getRoom();

        // then
        assertThat(newRoom.getRoomDetail().getName()).isEqualTo("거실");
        assertThat(newRoom.getAxis().getX()).isEqualTo(123);
        assertThat(newRoom.getAxis().getY()).isEqualTo(456);
        assertThat(newRoom.getAxis().getZ()).isEqualTo(1);
        assertThat(newRoom.getArea().getWidth()).isEqualTo(100);
        assertThat(newRoom.getArea().getHeight()).isEqualTo(200);
    }

    @Test
    @DisplayName("룸 수정 테스트")
    void testUpdateInfoRoom() throws AxisLimitExceededException, AreaLimitExceededException {
        // given
        Room room = getRoom();

        String name = "현관";

        // when
        room.updateDetail(name);

        // then
        assertThat(room.getRoomDetail().getName()).isEqualTo(name);
    }

    private Room getRoom() throws AxisLimitExceededException, AreaLimitExceededException {
        String name = "거실";
        Float x = 123f;
        Float y = 456f;
        Float z = 1f;
        Float width = 100F;
        Float height = 200F;

        Room newRoom = Room.create(1L, name, x, y, z, width, height, 1L);
        return newRoom;
    }
}