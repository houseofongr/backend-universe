package com.hoo.admin.application.port.in.home;

import com.hoo.admin.domain.home.Home;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.admin.domain.user.User;
import com.hoo.common.adapter.in.web.DateTimeFormatters;

import java.util.List;

public record QueryHomeResult(
        Long homeId,
        String homeName,
        String createdDate,
        String updatedDate,
        HouseInfo house,
        UserInfo user,
        List<RoomInfo> rooms
) {

    public static QueryHomeResult of(Home home, House house, User user) {
        return new QueryHomeResult(home.getHomeId().getId(),
                home.getHomeDetail().getName(),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(home.getBaseTime().getCreatedTime()),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(home.getBaseTime().getUpdatedTime()),
                HouseInfo.of(house),
                UserInfo.of(user),
                house.getRooms().stream().map(RoomInfo::of).toList()
        );
    }

    public record UserInfo(
            Long id,
            String nickname
    ) {

        public static UserInfo of(User user) {
            return new UserInfo(user.getUserInfo().getId(), user.getUserInfo().getNickname());
        }
    }

    public record HouseInfo(
            Float width,
            Float height,
            Long borderImageId
    ) {

        public static HouseInfo of(House house) {
            return new HouseInfo(
                    house.getArea().getWidth(),
                    house.getArea().getHeight(),
                    house.getBorderImageFile().getFileId().getId()
            );
        }
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
