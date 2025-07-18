package com.hoo.admin.application.port.in.home;

import com.hoo.admin.domain.home.Home;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.user.User;
import com.hoo.common.adapter.in.web.DateTimeFormatters;

import java.util.List;

public record QueryUserHomesResult(
        List<HomeInfo> homes
) {

    public record HomeInfo(
            Long id,
            String name,
            String createdDate,
            String updatedDate,
            HouseInfo baseHouse,
            UserInfo user
    ) {

        public static HomeInfo of(Home home, House house, User user) {
            return new HomeInfo(
                    home.getHomeId().getId(),
                    home.getHomeDetail().getName(),
                    DateTimeFormatters.DOT_DATE.getFormatter().format(home.getBaseTime().getCreatedTime()),
                    DateTimeFormatters.DOT_DATE.getFormatter().format(home.getBaseTime().getUpdatedTime()),
                    HouseInfo.of(house),
                    UserInfo.of(user)
            );
        }
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
            Long id,
            String title,
            String author,
            String description
    ) {
        public static HouseInfo of(House house) {
            return new HouseInfo(
                    house.getHouseId().getId(),
                    house.getHouseDetail().getTitle(),
                    house.getHouseDetail().getAuthor(),
                    house.getHouseDetail().getDescription().length() > 100 ? house.getHouseDetail().getDescription().substring(0, 100) + "..." : house.getHouseDetail().getDescription()
            );
        }
    }
}
