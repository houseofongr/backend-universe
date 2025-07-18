package com.hoo.universe.domain.vo;

import com.hoo.universe.domain.event.OverlapEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Outline {

    private static final int RENDER_WIDTH = 30;
    private static final int RENDER_HEIGHT = 10;

    private List<Point> points;

    public static Outline of(List<Point> points) {

        Set<Point> pointSet = new LinkedHashSet<>(points); // 중복 제거

        if (pointSet.size() < 3) throw new IllegalArgumentException("외곽선을 생성할 좌표 숫자가 부족합니다.");

        points = new ArrayList<>(pointSet);

        Outline outline = new Outline(points);
        outline.sortPointsClockwise(); // 시계방향 정렬

        return outline;
    }

    public static Outline of(Point... points) {
        return of(new ArrayList<>(Arrays.asList(points)));
    }

    public static Outline getRectangleBy2Point(Point s, Point e) {

        Set<Point> pointSet = new TreeSet<>();
        pointSet.add(s);
        pointSet.add(e);
        pointSet.add(Point.of(s.x(), e.y()));
        pointSet.add(Point.of(e.x(), s.y()));

        return Outline.of(new ArrayList<>(pointSet));
    }

    public static Outline getRectangleBy2Point(double x1, double y1, double x2, double y2) {
        return getRectangleBy2Point(Point.of(x1, y1), Point.of(x2, y2));
    }

    public Area getArea() {

        Path2D.Float path = new Path2D.Float();

        path.moveTo(points.getFirst().x(), points.getFirst().y());

        for (int i = 1; i < points.size(); i++) {
            path.lineTo(points.get(i).x(), points.get(i).y());
        }

        path.closePath();

        return new Area(path);
    }

    public OverlapEvent overlap(Outline outline) {

        Area a1 = getArea();
        Area a2 = outline.getArea();
        Area intersect = (Area) a1.clone();

        intersect.intersect(a2);

        if (!intersect.isEmpty()) return new OverlapEvent(true, renderIntersectArea(a1, a2, intersect));
        return OverlapEvent.no();
    }

    public OverlapEvent overlap(List<Outline> outlines) {

        for (Outline s : outlines) {
            OverlapEvent event = s.overlap(this);
            if (event.isOverlapped()) return event;
        }

        return OverlapEvent.no();
    }

    public Point[] getRectangleFarthestPoints() {

        if (points.size() != 4) throw new IllegalStateException("현재 도형은 사각형이 아닙니다.");

        Point[] result = new Point[2];

        double maxDistance = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double dx = points.get(i).x() - points.get(j).x();
                double dy = points.get(i).y() - points.get(j).y();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    result[0] = points.get(i);
                    result[1] = points.get(j);
                }
            }
        }

        return result;
    }

    public double[] getAverageCenter() {

        double[] ret = new double[2];
        for (Point p : points) {
            ret[0] += p.x();
            ret[1] += p.y();
        }
        ret[0] /= points.size();
        ret[1] /= points.size();

        return ret;
    }

    public String render() {

        Area a = getArea();
        char[][] canvas = new char[RENDER_HEIGHT][RENDER_WIDTH];

        for (int y = 0; y < RENDER_HEIGHT; y++) {
            double ny = 1.0 - ((double) y / (RENDER_HEIGHT - 1));

            for (int x = 0; x < RENDER_WIDTH; x++) {
                double nx = (double) x / (RENDER_WIDTH - 1);

                if (a.contains(nx, ny)) {
                    canvas[y][x] = '█';
                } else {
                    canvas[y][x] = '░';
                }
            }
        }

        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < RENDER_HEIGHT; y++) {
            sb.append(new String(canvas[y])).append("\n");
        }

        return sb.toString();
    }

    private String renderIntersectArea(Area a1, Area a2, Area intersect) {

        char[][] canvas = new char[RENDER_HEIGHT][RENDER_WIDTH];

        for (int y = 0; y < RENDER_HEIGHT; y++) {
            double ny = 1.0 - ((double) y / (RENDER_HEIGHT - 1));

            for (int x = 0; x < RENDER_WIDTH; x++) {
                double nx = (double) x / (RENDER_WIDTH - 1);

                if (intersect.contains(nx, ny)) {
                    canvas[y][x] = '█';
                } else if (a1.contains(nx, ny) || a2.contains(nx, ny)) {
                    canvas[y][x] = '▒';
                } else {
                    canvas[y][x] = '░';
                }
            }
        }

        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < RENDER_HEIGHT; y++) {
            sb.append(new String(canvas[y])).append("\n");
        }

        return sb.toString();
    }

    private void sortPointsClockwise() {

        double[] center = getAverageCenter();
        this.points = points.stream()
                .sorted(Comparator.comparingDouble(p -> Math.atan2(p.x() - center[0], p.y() - center[1])))
                .toList();
    }
}
