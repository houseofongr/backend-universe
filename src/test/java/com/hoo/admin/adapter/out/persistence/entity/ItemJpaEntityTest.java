package com.hoo.admin.adapter.out.persistence.entity;

import com.hoo.admin.domain.item.*;
import com.hoo.common.adapter.out.persistence.entity.*;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemJpaEntityTest {

    @Test
    @DisplayName("사각형 아이템 변환 테스트")
    void testRectangleToRectangleItemJpaEntity() throws Exception {
        // given
        Item rectangleItem = MockEntityFactoryService.getRectangleItem();

        // when
        ItemJpaEntity itemJpaEntity = ItemJpaEntity.create(rectangleItem);

        // then
        assertThat(itemJpaEntity.getName()).isEqualTo(rectangleItem.getItemDetail().getName());

        ItemShapeJpaEntity shapeJpaEntity = itemJpaEntity.getShape();
        Shape shape = rectangleItem.getShape();
        assertThat(shapeJpaEntity.getX()).isEqualTo(shape.getX());
        assertThat(shapeJpaEntity.getY()).isEqualTo(shape.getY());

        ItemShapeRectangleJpaEntity rectangleJpaEntity = (ItemShapeRectangleJpaEntity) shapeJpaEntity;
        Rectangle rectangle = (Rectangle) shape;
        assertThat(rectangleJpaEntity.getWidth()).isEqualTo(rectangle.getWidth());
        assertThat(rectangleJpaEntity.getHeight()).isEqualTo(rectangle.getHeight());
        assertThat(rectangleJpaEntity.getRotation()).isEqualTo(rectangle.getRotation());
    }

    @Test
    @DisplayName("원형 아이템 변환 테스트")
    void testCircleToItemShapeCircleJpaEntity() throws Exception {
        // given
        Item circleItem = MockEntityFactoryService.getCircleItem();

        // when
        ItemJpaEntity itemJpaEntity = ItemJpaEntity.create(circleItem);

        // then
        assertThat(itemJpaEntity.getName()).isEqualTo(circleItem.getItemDetail().getName());

        ItemShapeJpaEntity shapeJpaEntity = itemJpaEntity.getShape();
        Shape shape = circleItem.getShape();
        assertThat(shapeJpaEntity.getX()).isEqualTo(shape.getX());
        assertThat(shapeJpaEntity.getY()).isEqualTo(shape.getY());

        ItemShapeCircleJpaEntity circleJpaEntity = (ItemShapeCircleJpaEntity) shapeJpaEntity;
        Circle circle = (Circle) shape;
        assertThat(circleJpaEntity.getRadius()).isEqualTo(circle.getRadius());
    }

    @Test
    @DisplayName("타원형 아이템 변환 테스트")
    void testEllipseToItemShapeEllipseJpaEntity() throws Exception {
        // given
        Item ellipseItem = MockEntityFactoryService.getEllipseItem();

        // when
        ItemJpaEntity itemJpaEntity = ItemJpaEntity.create(ellipseItem);

        // then
        assertThat(itemJpaEntity.getName()).isEqualTo(ellipseItem.getItemDetail().getName());

        ItemShapeJpaEntity shapeJpaEntity = itemJpaEntity.getShape();
        Shape shape = ellipseItem.getShape();
        assertThat(shapeJpaEntity.getX()).isEqualTo(shape.getX());
        assertThat(shapeJpaEntity.getY()).isEqualTo(shape.getY());

        ItemShapeEllipseJpaEntity ellipseJpaEntity = (ItemShapeEllipseJpaEntity) shapeJpaEntity;
        Ellipse ellipse = (Ellipse) shape;
        assertThat(ellipseJpaEntity.getRadiusX()).isEqualTo(ellipse.getRadiusX());
        assertThat(ellipseJpaEntity.getRadiusY()).isEqualTo(ellipse.getRadiusY());
        assertThat(ellipseJpaEntity.getRotation()).isEqualTo(ellipse.getRotation());
    }

}