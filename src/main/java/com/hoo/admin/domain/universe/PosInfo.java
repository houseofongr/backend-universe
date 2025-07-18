package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class PosInfo {
    private Float sx;
    private Float sy;
    private Float ex;
    private Float ey;

    public PosInfo(Float sx, Float sy, Float ex, Float ey) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
    }

    public void update(Float sx, Float sy, Float ex, Float ey) {
        this.sx = sx != null ? sx : this.sx;
        this.sy = sy != null ? sy : this.sy;
        this.ex = ex != null ? ex : this.ex;
        this.ey = ey != null ? ey : this.ey;
    }
}