package com.hoo.aar.application.port.in.home;

import com.hoo.admin.domain.item.ItemType;

import java.util.List;

public record QueryRoomItemsResult(
        RoomInfo room,
        List<ItemData> items
) {

    public record RoomInfo(
            String name,
            Float width,
            Float height,
            Long imageId
    ) {

    }

    public record ItemData(
            Long id,
            String name,
            ItemType itemType,
            CircleData circleData,
            RectangleData rectangleData,
            EllipseData ellipseData
    ) {

    }

    public record CircleData(
            Float x,
            Float y,
            Float radius
    ) {

    }

    public record RectangleData(
            Float x,
            Float y,
            Float width,
            Float height,
            Float rotation
    ) {

    }

    public record EllipseData(
            Float x,
            Float y,
            Float radiusX,
            Float radiusY,
            Float rotation
    ) {

    }
}
