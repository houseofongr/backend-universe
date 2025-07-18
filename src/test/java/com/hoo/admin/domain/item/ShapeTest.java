package com.hoo.admin.domain.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShapeTest {

    @Test
    @DisplayName("직사각형 생성 테스트")
    void testCreateRectangle() {
        // given
        Float x = 100f;
        Float y = 100f;
        Float width = 10f;
        Float height = 10f;
        Float angle = 5f;

        // when
        Shape rectangle = new Rectangle(x, y, width, height, angle);

        // then
        assertThat(rectangle.getItemType()).isEqualTo(ItemType.RECTANGLE);
        assertThat(rectangle.getX()).isEqualTo(100f);
        assertThat(rectangle.getY()).isEqualTo(100f);
        assertThat(((Rectangle) rectangle).getWidth()).isEqualTo(10f);
        assertThat(((Rectangle) rectangle).getHeight()).isEqualTo(10f);
        assertThat(((Rectangle) rectangle).getRotation()).isEqualTo(5f);

    }

    @Test
    @DisplayName("원형 생성 테스트")
    void testCreateCircle() {
        // given
        Float x = 200f;
        Float y = 200f;
        Float radius = 10.5f;

        // when
        Shape circle = new Circle(x, y, radius);

        // then
        assertThat(circle.getItemType()).isEqualTo(ItemType.CIRCLE);
        assertThat(circle.getX()).isEqualTo(200f);
        assertThat(circle.getY()).isEqualTo(200f);
        assertThat(((Circle) circle).getRadius()).isEqualTo(10.5f);
    }

    @Test
    @DisplayName("타원형 생성 테스트")
    void testCreateEllipse() {
        // given
        Float x = 500f;
        Float y = 500f;
        Float width = 15f;
        Float height = 15f;
        Float angle = 90f;

        // when
        Shape ellipse = new Ellipse(x, y, width, height, angle);

        // then
        assertThat(ellipse.getItemType()).isEqualTo(ItemType.ELLIPSE);
        assertThat(ellipse.getX()).isEqualTo(500f);
        assertThat(ellipse.getY()).isEqualTo(500f);
        assertThat(((Ellipse) ellipse).getRadiusX()).isEqualTo(15f);
        assertThat(((Ellipse) ellipse).getRadiusY()).isEqualTo(15f);
        assertThat(((Ellipse) ellipse).getRotation()).isEqualTo(90f);
    }

}