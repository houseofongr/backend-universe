package com.hoo.universe.domain.event.space;

import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.event.OverlapEvent;
import com.hoo.universe.domain.vo.Outline;

public record SpaceMoveEvent(
        UniverseID universeID,
        SpaceID spaceID,
        OverlapEvent overlapEvent,
        Outline outline
) {
}
