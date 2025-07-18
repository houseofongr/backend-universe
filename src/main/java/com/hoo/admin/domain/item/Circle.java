package com.hoo.admin.domain.item;

import lombok.Getter;

@Getter
public class Circle extends Shape {

    private final Float radius;

    public Circle(Float x, Float y, Float radius) {
        super(ItemType.CIRCLE, x, y);
        this.radius = radius;
    }
}
