package com.hoo.admin.domain;

import com.hoo.admin.domain.exception.AxisLimitExceededException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Axis {

    private Float x;
    private Float y;
    private Float z;

    public Axis(Float x, Float y, Float z) {
        validateLimit(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Axis from(Float x, Float y) {
        return new Axis(x, y, 0f);
    }

    private void validateLimit(Float... params) {
        for (Float param : params) {
            if (param < 0 || param > Short.MAX_VALUE) throw new AxisLimitExceededException();
        }
    }

    @Deprecated
    void update(Float x, Float y, Float z) throws AxisLimitExceededException {

        if (x == null && y == null && z == null) return;

        List<Float> updatable = new ArrayList<>();

        if (x != null) updatable.add(x);
        if (y != null) updatable.add(y);
        if (z != null) updatable.add(z);

        validateLimit(updatable.toArray(Float[]::new));

        this.x = x == null ? this.x : x;
        this.y = y == null ? this.y : y;
        this.z = z == null ? this.z : z;
    }
}
