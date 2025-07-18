package com.hoo.universe.test.domain;

import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;

import java.util.ArrayList;
import java.util.List;

public class ShapeTestData {

    public static List<Outline> createNonOverlappingShapes(int size) {

        if (size > 10) throw new UnsupportedOperationException("최대 사이즈 개수 초과");

        List<Outline> outlines = new ArrayList<>();

        float unitSize = (float) 1 / size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                outlines.add(Outline.getRectangleBy2Point(
                        Point.of(unitSize * i, unitSize * j),
                        Point.of(unitSize * (i + 1), unitSize * (j + 1))
                ));
            }
        }

        return outlines;
    }
}
