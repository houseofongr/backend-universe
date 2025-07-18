package com.hoo.admin.domain.item;

import lombok.Getter;

@Getter
public class ItemDetail {
    private String name;

    public ItemDetail(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        if (name != null && !name.isBlank()) this.name = name;
    }
}
