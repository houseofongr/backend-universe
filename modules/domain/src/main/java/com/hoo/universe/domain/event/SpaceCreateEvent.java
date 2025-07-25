package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Space;

public record SpaceCreateEvent(
        boolean maxSpaceExceeded,
        boolean maxChildExceeded,
        OverlapEvent overlapEvent,
        Space newSpace
) {
}
