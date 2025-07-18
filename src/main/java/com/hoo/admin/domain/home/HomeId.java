package com.hoo.admin.domain.home;

import lombok.Getter;

@Getter
public class HomeId {
    private final Long id;

    public HomeId(Long id) {
        this.id = id;
    }
}
