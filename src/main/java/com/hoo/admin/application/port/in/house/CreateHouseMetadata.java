package com.hoo.admin.application.port.in.house;

import java.util.List;

public record CreateHouseMetadata(
        HouseData house,
        List<RoomData> rooms
) {
    public record HouseData(
            String title,
            String author,
            String description,
            String houseForm,
            String borderForm,
            Float width,
            Float height
    ) {

    }

    public record RoomData(
            String form,
            String name,
            Float x,
            Float y,
            Float z,
            Float width,
            Float height
    ) {

    }
}
