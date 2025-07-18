package com.hoo.admin.application.port.in.house;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.hoo.common.util.GsonUtil.gson;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateHouseMetadataTest {

    @Test
    @DisplayName("하우스 메타데이터 생성 테스트")
    void testCreateHouseMetadata() {
        // given

        // when
        CreateHouseMetadata metadata = getCreateHouseMetadata();

        // then
        assertThat(metadata.house()).isNotNull();
        assertThat(metadata.rooms()).hasSize(2);
    }

    public static CreateHouseMetadata getCreateHouseMetadata() {
        return new CreateHouseMetadata(
                new CreateHouseMetadata.HouseData("cozy house", "leaf", "this is cozy house.", "house", "border", 5000f, 5000f),
                List.of(
                        new CreateHouseMetadata.RoomData("room1", "거실", 123f, 456f, 1f, 100f, 200f),
                        new CreateHouseMetadata.RoomData("room2", "주방", 234f, 345f, 2f, 200f, 200f)
                )
        );
    }

    public static String getCreateHouseMetadataJson() {
        return gson.toJson(getCreateHouseMetadata());
    }

}