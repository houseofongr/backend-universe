package com.hoo.admin.adapter.out.persistence.mapper;

import com.hoo.admin.application.port.in.item.ItemData;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.item.*;
import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.ItemShapeCircleJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.ItemShapeEllipseJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.ItemShapeRectangleJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final SoundSourceMapper soundSourceMapper;

    public Item mapToDomainEntity(ItemJpaEntity itemJpaEntity) {
        return switch (itemJpaEntity.getShape()) {
            case ItemShapeRectangleJpaEntity itemShapeRectangleJpaEntity -> Item.load(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getHome().getId(),
                    itemJpaEntity.getRoom().getId(),
                    itemJpaEntity.getHome().getUser().getId(),
                    itemJpaEntity.getName(),
                    new Rectangle(itemShapeRectangleJpaEntity.getX(),
                            itemShapeRectangleJpaEntity.getY(),
                            itemShapeRectangleJpaEntity.getWidth(),
                            itemShapeRectangleJpaEntity.getHeight(),
                            itemShapeRectangleJpaEntity.getRotation()),
                    itemJpaEntity.getSoundSources().stream().map(soundSourceMapper::mapToDomainEntity).toList()
            );
            case ItemShapeCircleJpaEntity itemShapeCircleJpaEntity -> Item.load(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getHome().getId(),
                    itemJpaEntity.getRoom().getId(),
                    itemJpaEntity.getHome().getUser().getId(),
                    itemJpaEntity.getName(),
                    new Circle(itemShapeCircleJpaEntity.getX(),
                            itemShapeCircleJpaEntity.getY(),
                            itemShapeCircleJpaEntity.getRadius()),
                    itemJpaEntity.getSoundSources().stream().map(soundSourceMapper::mapToDomainEntity).toList()
            );
            case ItemShapeEllipseJpaEntity itemShapeEllipseJpaEntity -> Item.load(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getHome().getId(),
                    itemJpaEntity.getRoom().getId(),
                    itemJpaEntity.getHome().getUser().getId(),
                    itemJpaEntity.getName(),
                    new Ellipse(itemShapeEllipseJpaEntity.getX(),
                            itemShapeEllipseJpaEntity.getY(),
                            itemShapeEllipseJpaEntity.getRadiusX(),
                            itemShapeEllipseJpaEntity.getRadiusY(),
                            itemShapeEllipseJpaEntity.getRotation()),
                    itemJpaEntity.getSoundSources().stream().map(soundSourceMapper::mapToDomainEntity).toList()
            );
            case null, default -> {

                throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE);
            }
        };
    }

    public Shape mapToShape(ItemData itemData) {
        return switch (itemData.itemType()) {
            case RECTANGLE -> new Rectangle(
                    itemData.rectangleData().x(),
                    itemData.rectangleData().y(),
                    itemData.rectangleData().width(),
                    itemData.rectangleData().height(),
                    itemData.rectangleData().rotation());
            case CIRCLE -> new Circle(
                    itemData.circleData().x(),
                    itemData.circleData().y(),
                    itemData.circleData().radius()
            );
            case ELLIPSE -> new Ellipse(
                    itemData.ellipseData().x(),
                    itemData.ellipseData().y(),
                    itemData.ellipseData().radiusX(),
                    itemData.ellipseData().radiusY(),
                    itemData.ellipseData().rotation()
            );
        };
    }
}
