package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpaceMetadata {

    private final UUID backgroundID;
    private final boolean hidden;

    public static SpaceMetadata create(UUID imageID, Boolean hidden) {
        return new SpaceMetadata(imageID, orElseNotHidden(hidden));
    }

    public SpaceMetadata update(Boolean hidden) {
        return new SpaceMetadata(backgroundID, getOrDefault(hidden, this.hidden));
    }

    public SpaceMetadata overwrite(UUID backgroundID) {
        return new SpaceMetadata(backgroundID, hidden);
    }

    private static boolean orElseNotHidden(Boolean hidden) {
        return hidden != null && hidden;
    }

    private  <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
