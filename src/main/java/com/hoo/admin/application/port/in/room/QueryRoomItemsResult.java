package com.hoo.admin.application.port.in.room;

import com.hoo.admin.application.port.in.item.ItemData;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.admin.domain.item.Item;

import java.util.List;

public record QueryRoomItemsResult(
        RoomData room,
        List<ItemData> items
) {

    public static QueryRoomItemsResult of(Room room, List<Item> items) {
        return new QueryRoomItemsResult(
                new RoomData(
                        room.getRoomDetail().getName(),
                        room.getArea().getWidth(),
                        room.getArea().getHeight(),
                        room.getImageFile().getFileId().getId()
                ),
                items.stream().map(ItemData::of).toList()
        );
    }

    public record RoomData(
            String name,
            Float width,
            Float height,
            Long imageId
    ) {

    }

}
