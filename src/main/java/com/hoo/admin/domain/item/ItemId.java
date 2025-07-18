package com.hoo.admin.domain.item;

import lombok.Getter;

@Getter
public class ItemId {
    private final Long id;

    public ItemId(Long id) {
        this.id = id;
    }
}
