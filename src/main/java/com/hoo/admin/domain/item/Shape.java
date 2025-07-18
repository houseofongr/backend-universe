package com.hoo.admin.domain.item;

import lombok.Getter;

@Getter
public abstract class Shape {
    private final ItemType itemType;
    private final Float x;
    private final Float y;

    protected Shape(ItemType itemType, Float x, Float y) {
        this.itemType = itemType;
        this.x = x;
        this.y = y;
    }
}
