package com.hoo.universe.domain.event.space;

import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.event.OverlapEvent;

public record SpaceCreateEvent(
        boolean maxSpaceExceeded,
        boolean maxChildExceeded,
        OverlapEvent overlapEvent,
        Space newSpace
) {
}
