package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpaceMetadata {

    private final UUID backgroundID;
    private final boolean hidden;

    public static SpaceMetadata create(UUID imageID, Boolean hidden) {
        return new SpaceMetadata(imageID, orElseFalse(hidden));
    }

    public SpaceMetadata update(Boolean hidden) {
        return new SpaceMetadata(backgroundID, getOrDefault(hidden, this.hidden));
    }

    public SpaceMetadata overwrite(UUID backgroundID) {
        return new SpaceMetadata(backgroundID, hidden);
    }

}
