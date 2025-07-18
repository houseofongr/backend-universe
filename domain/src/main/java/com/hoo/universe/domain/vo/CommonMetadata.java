package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonMetadata {

    private final String title;
    private final String description;
    private final ZonedDateTime createdTime;
    private final ZonedDateTime updatedTime;

    public static CommonMetadata create(String title, String description) {
        return new CommonMetadata(title, description, ZonedDateTime.now(), ZonedDateTime.now());
    }

    public CommonMetadata update(String title, String description) {
        return new CommonMetadata(getOrDefault(title, this.title), getOrDefault(description, this.description), createdTime, ZonedDateTime.now());
    }

    private  <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

}
