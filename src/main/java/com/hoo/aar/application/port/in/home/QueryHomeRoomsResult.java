package com.hoo.aar.application.port.in.home;

import java.util.List;

public record QueryHomeRoomsResult(
        String homeName,
        HouseInfo house,
        List<RoomData> rooms
) {
    public record HouseInfo(
            Float width,
            Float height,
            Long borderImageId
    ) {

    }

    public record RoomData(
            Long roomId,
            String name,
            Float x,
            Float y,
            Float z,
            Float width,
            Float height,
            Long imageId
    ) {

    }
}
