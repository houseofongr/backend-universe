package com.hoo.admin.domain.home;

import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.house.HouseId;
import com.hoo.admin.domain.user.User;
import com.hoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Home {
    private final HomeId homeId;
    private final HouseId houseId;
    private final Long ownerId;
    private final HomeDetail homeDetail;
    private final BaseTime baseTime;

    private Home(HomeId homeId, HouseId houseId, Long ownerId, HomeDetail homeDetail, BaseTime baseTime) {
        this.homeId = homeId;
        this.houseId = houseId;
        this.ownerId = ownerId;
        this.homeDetail = homeDetail;
        this.baseTime = baseTime;
    }

    public static Home create(Long id, House house, User user) {
        return new Home(new HomeId(id), house.getHouseId(), user.getUserInfo().getId(), new HomeDetail(house, user), null);
    }

    public static Home load(Long id, Long houseId, Long userId, String homeName, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Home(new HomeId(id), new HouseId(houseId), userId, new HomeDetail(homeName), new BaseTime(createdTime, updatedTime));
    }

    public void updateName(String homeName) {
        homeDetail.updateName(homeName);
    }
}
