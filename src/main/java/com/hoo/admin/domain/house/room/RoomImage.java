package com.hoo.admin.domain.house.room;

import lombok.Getter;

@Getter
public class RoomImage {

    private final Long imageId;

    public RoomImage(Long imageId) {
        this.imageId = imageId;
    }
}
