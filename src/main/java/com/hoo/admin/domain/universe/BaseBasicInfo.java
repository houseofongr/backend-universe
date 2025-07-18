package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class BaseBasicInfo {
    private String title;
    private String description;

    public BaseBasicInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void update(String title, String description) {
        this.title = title!= null ? title : this.title;
        this.description = description != null ? description : this.description;
    }
}
