package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PieceMetadata {

    private final UUID imageID;
    private final boolean hidden;

    public static PieceMetadata create(UUID imageID, Boolean hidden) {
        return new PieceMetadata(imageID, orElseFalse(hidden));
    }

    public PieceMetadata update(Boolean hidden) {
        return new PieceMetadata(imageID, getOrDefault(hidden, this.hidden));
    }
}
