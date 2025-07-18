package com.hoo.admin.domain.item;

import lombok.Getter;

@Getter
public class Ellipse extends Shape {

    private final Float radiusX;
    private final Float radiusY;
    private final Float rotation;

    public Ellipse(Float x, Float y, Float radiusX, Float radiusY, Float rotation) {
        super(ItemType.ELLIPSE, x, y);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.rotation = rotation;
    }
}
