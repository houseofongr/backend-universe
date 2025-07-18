package com.hoo.universe.domain.vo;

public record Point(
        double x,
        double y
) implements Comparable<Point> {

    public static Point of(double x, double y) {
        if (x < 0 || x > 1 || y < 0 || y > 1) throw new IllegalArgumentException("좌표 범위를 초과했습니다.");
        return new Point(x, y);
    }

    @Override
    public int compareTo(Point e) {
        if (this.x == e.x) return Double.compare(this.y, e.y);
        return Double.compare(this.x, e.x);
    }

}
