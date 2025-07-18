package com.hoo.universe.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OutlineTest {

    @Test
    @DisplayName("생성된 외곽선 시계방향으로 정렬하는지 확인")
    void testSort() {
        // given
        Point s = Point.of(0.3f, 0.2f);
        Point e = Point.of(0.1f, 0.3f);

        // when
        Outline rectangle = Outline.getRectangleBy2Point(s, e);

        // then
        assertThat(rectangle.getPoints().get(0)).isEqualTo(Point.of(0.1f, 0.2f));
        assertThat(rectangle.getPoints().get(1)).isEqualTo(Point.of(0.1f, 0.3f));
        assertThat(rectangle.getPoints().get(2)).isEqualTo(Point.of(0.3f, 0.3f));
        assertThat(rectangle.getPoints().get(3)).isEqualTo(Point.of(0.3f, 0.2f));
    }

    @Test
    @DisplayName("시작점과 끝점이 같은 직사각형을 만들때 오류 발생하는지 확인")
    void testEqualStartAndEnd() {
        // given

        // then
        assertThatThrownBy(() -> Outline.getRectangleBy2Point(
                Point.of(0.1f, 0.2f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("외곽선을 생성할 좌표 숫자가 부족합니다.");
    }

    @Test
    @DisplayName("범위 초과할때 오류 발생하는지 확인")
    void testOutOfBound() {
        assertThatThrownBy(() -> Outline.getRectangleBy2Point(
                Point.of(1f, 2f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌표 범위를 초과했습니다.");
    }

    @Test
    @DisplayName("3개 미만의 점으로 도형을 만들때 오류 발생하는지 확인")
    void testLessThan3() {
        assertThatThrownBy(() -> Outline.of(
                Point.of(0.1f, 0.1f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("외곽선을 생성할 좌표 숫자가 부족합니다.");

        assertThatThrownBy(() -> Outline.of(
                Point.of(0.1f, 0.1f),
                Point.of(0.1f, 0.1f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("외곽선을 생성할 좌표 숫자가 부족합니다.");
    }

    @Test
    @DisplayName("사각형에서 대각선 두 점 반환하기")
    void testGetRectangleFarthestPoints() {
        Point s = Point.of(0.2f, 0.3f);
        Point e = Point.of(0.4f, 0.5f);

        Outline rectangle = Outline.getRectangleBy2Point(s, e);
        Point[] points = rectangle.getRectangleFarthestPoints();

        assertThat(points[0]).isEqualTo(s);
        assertThat(points[1]).isEqualTo(e);
    }
    
    @Test
    @DisplayName("중심 구하기")
    void testGetAverageCenter() {
        // given
        Point s = Point.of(0, 0);
        Point e = Point.of(1f, 1f);

        Outline rectangle = Outline.getRectangleBy2Point(s, e);
        double[] centroid = rectangle.getAverageCenter();

        assertThat(centroid[0]).isEqualTo(0.5f);
        assertThat(centroid[1]).isEqualTo(0.5f);
    }
}