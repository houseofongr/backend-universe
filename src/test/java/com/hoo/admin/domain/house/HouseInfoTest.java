package com.hoo.admin.domain.house;

import com.hoo.admin.domain.exception.RoomNameNotFoundException;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HouseInfoTest {

    String title = "cozy house";
    String author = "leaf";
    String description = "this is cozy house.";
    Float width = 5000F;
    Float height = 5000F;

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws Exception {
        // given

        // when
        House newHouse = MockEntityFactoryService.getHouse();

        // then
        assertThat(newHouse.getRooms()).hasSize(2);
        assertThat(newHouse.getHouseDetail().getTitle()).isEqualTo("cozy house");
        assertThat(newHouse.getHouseDetail().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getArea().getWidth()).isEqualTo(5000);
        assertThat(newHouse.getArea().getHeight()).isEqualTo(5000);
    }

    @Test
    @DisplayName("하우스 수정 테스트")
    void testUpdateInfo() throws Exception {
        // given
        House newHouse = MockEntityFactoryService.getHouse();

        String title = "not cozy house";
        String author = null;
        String description = "this is not cozy house.";

        // when
        newHouse.updateDetail(title, author, description);

        // then
        assertThat(newHouse.getHouseDetail().getTitle()).isEqualTo(title);
        assertThat(newHouse.getHouseDetail().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getHouseDetail().getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("룸 수정 테스트")
    void testUpdateRoomInfo() throws Exception {
        // given
        House houseWithRoom = MockEntityFactoryService.getHouse();
        String originalName = "거실";
        String newName = "욕실";

        // when
        houseWithRoom.updateRoomInfo(originalName, newName);

        // then
        assertThat(houseWithRoom.getRooms()).anySatisfy(
                room -> assertThat(room.getRoomDetail().getName()).isEqualTo("욕실")
        );

        assertThatThrownBy(() -> houseWithRoom.updateRoomInfo(originalName, originalName))
                .isInstanceOf(RoomNameNotFoundException.class)
                .hasMessage("house 'cozy house' doesn't have room named '거실'.");

    }
}