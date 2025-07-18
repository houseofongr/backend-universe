package com.hoo.admin.domain.house.room;

import lombok.Getter;

@Getter
public class RoomDetail {

    private String name;

    public RoomDetail(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = (name == null || name.isBlank()) ? this.name : name;
    }
}
