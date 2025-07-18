package com.hoo.universe.domain.event;

public record OverlapEvent(
        boolean isOverlapped,
        String renderOverlapStatus
) {

    public static OverlapEvent no() {
        return new OverlapEvent(false, null);
    }

}
