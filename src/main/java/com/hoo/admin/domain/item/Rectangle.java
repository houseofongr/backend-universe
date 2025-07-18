package com.hoo.admin.domain.item;

import lombok.Getter;

@Getter
public class Rectangle extends Shape {

    private final Float width;
    private final Float height;
    private final Float rotation;

    public Rectangle(Float x, Float y, Float width, Float height, Float rotation) {
        super(ItemType.RECTANGLE, x, y);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

}
