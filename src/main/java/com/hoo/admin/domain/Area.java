package com.hoo.admin.domain;

import com.hoo.admin.domain.exception.AreaLimitExceededException;
import lombok.Getter;

@Getter
public class Area {

    private Float width;
    private Float height;

    public Area(Float width, Float height) {
        validateLimit(height, width);
        this.height = height;
        this.width = width;
    }

    private void validateLimit(Float... params) {
        for (Float param : params) {
            if (param <= 0 || param > Short.MAX_VALUE) throw new AreaLimitExceededException();
        }
    }

    @Deprecated
    void update(Float width, Float height) throws AreaLimitExceededException {

        if (width != null && height != null) validateLimit(width, height);
        else if (width != null) validateLimit(width);
        else if (height != null) validateLimit(height);
        else return;

        this.width = width == null ? this.width : width;
        this.height = height == null ? this.height : height;
    }
}
