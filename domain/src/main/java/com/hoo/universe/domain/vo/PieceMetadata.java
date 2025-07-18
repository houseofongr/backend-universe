package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PieceMetadata {

    private final UUID imageID;
    private final boolean hidden;

    public static PieceMetadata create(UUID imageID, Boolean hidden) {
        return new PieceMetadata(imageID, orElseNotHidden(hidden));
    }

    private static boolean orElseNotHidden(Boolean hidden) {
        return hidden != null && hidden;
    }

    public PieceMetadata update(Boolean hidden) {
        return new PieceMetadata(imageID, getOrDefault(hidden, this.hidden));
    }

    private <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
