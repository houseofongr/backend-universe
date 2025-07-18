package com.hoo.admin.application.port.in.item;

import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.item.*;
import jakarta.validation.constraints.NotNull;

public record ItemData(
        Long id,
        @NotNull String name,
        @NotNull ItemType itemType,
        CircleData circleData,
        RectangleData rectangleData,
        EllipseData ellipseData
) {

    public static ItemData of(Item item) {
        return switch (item.getShape()) {
            case Rectangle rectangle -> new ItemData(
                    item.getItemId().getId(),
                    item.getItemDetail().getName(),
                    ItemType.RECTANGLE,
                    null,
                    new ItemData.RectangleData(
                            rectangle.getX(),
                            rectangle.getY(),
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            rectangle.getRotation()),
                    null
            );
            case Circle circle -> new ItemData(
                    item.getItemId().getId(),
                    item.getItemDetail().getName(),
                    ItemType.CIRCLE,
                    new ItemData.CircleData(
                            circle.getX(),
                            circle.getY(),
                            circle.getRadius()),
                    null,
                    null
            );
            case Ellipse ellipse -> new ItemData(
                    item.getItemId().getId(),
                    item.getItemDetail().getName(),
                    ItemType.ELLIPSE,
                    null,
                    null,
                    new ItemData.EllipseData(
                            ellipse.getX(),
                            ellipse.getY(),
                            ellipse.getRadiusX(),
                            ellipse.getRadiusY(),
                            ellipse.getRotation())
            );
            case null, default -> throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE);
        };
    }

    public record CircleData(
            Float x,
            Float y,
            Float radius
    ) {

    }

    public record RectangleData(
            Float x,
            Float y,
            Float width,
            Float height,
            Float rotation
    ) {

    }

    public record EllipseData(
            Float x,
            Float y,
            Float radiusX,
            Float radiusY,
            Float rotation
    ) {

    }
}
