package com.hoo.admin.domain.house;

import lombok.Getter;

@Getter
public class HouseDetail {

    private String title;
    private String owner;
    private String description;

    public HouseDetail(String title, String owner, String description) {
        this.title = title;
        this.owner = owner;
        this.description = description;
    }

    public void update(String title, String owner, String description) {
        this.title = title == null ? this.title : title;
        this.owner = owner == null ? this.owner : owner;
        this.description = description == null ? this.description : description;
    }
}
