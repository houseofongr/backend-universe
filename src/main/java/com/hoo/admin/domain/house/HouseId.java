package com.hoo.admin.domain.house;

import lombok.Getter;

@Getter
public class HouseId {
    private final Long id;

    public HouseId(Long id) {
        this.id = id;
    }
}
