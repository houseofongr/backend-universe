package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Space.SpaceID;

import java.util.UUID;

public record SpaceFileOverwriteEvent(
        SpaceID spaceID,
        UUID oldInnerImageID,
        UUID newInnerImageID
) {
}
