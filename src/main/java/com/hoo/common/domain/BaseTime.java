package com.hoo.common.domain;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class BaseTime {
    private final ZonedDateTime createdTime;
    private final ZonedDateTime updatedTime;

    public BaseTime(ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
