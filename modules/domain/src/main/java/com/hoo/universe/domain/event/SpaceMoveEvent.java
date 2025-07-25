package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.vo.Outline;

public record SpaceMoveEvent(
        UniverseID universeID,
        SpaceID spaceID,
        OverlapEvent overlapEvent,
        Outline outline
) {
}
