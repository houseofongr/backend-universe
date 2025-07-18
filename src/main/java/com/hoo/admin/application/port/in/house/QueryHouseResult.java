package com.hoo.admin.application.port.in.house;

import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.adapter.in.web.DateTimeFormatters;

import java.util.List;

public record QueryHouseResult(
        HouseInfo house,
        List<RoomInfo> rooms
) {

    public static QueryHouseResult of(House house) {
        return new QueryHouseResult(
                new HouseInfo(
                        house.getHouseId().getId(),
                        house.getHouseDetail().getTitle(),
                        house.getHouseDetail().getAuthor(),
                        house.getHouseDetail().getDescription(),
                        DateTimeFormatters.ENGLISH_DATE.getFormatter().format(house.getBaseTime().getCreatedTime()),
                        DateTimeFormatters.ENGLISH_DATE.getFormatter().format(house.getBaseTime().getUpdatedTime()),
                        house.getArea().getWidth(),
                        house.getArea().getHeight(),
                        house.getBorderImageFile().getFileId().getId()
                ),
                house.getRooms().stream().map(RoomInfo::of).toList()
        );
    }

    public record HouseInfo(
            Long houseId,
            String title,
            String author,
            String description,
            String createdDate,
            String updatedDate,
            Float width,
            Float height,
            Long borderImageId
    ) {
    }

    public record RoomInfo(
            Long roomId,
            String name,
            Float x,
            Float y,
            Float z,
            Float width,
            Float height,
            Long imageId
    ) {

        public static RoomInfo of(Room room) {
            return new RoomInfo(
                    room.getRoomId().getId(),
                    room.getRoomDetail().getName(),
                    room.getAxis().getX(),
                    room.getAxis().getY(),
                    room.getAxis().getZ(),
                    room.getArea().getWidth(),
                    room.getArea().getHeight(),
                    room.getImageFile().getFileId().getId()
            );
        }
    }
}
