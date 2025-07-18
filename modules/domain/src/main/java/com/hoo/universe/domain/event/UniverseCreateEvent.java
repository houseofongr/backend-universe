package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Universe;

public record UniverseCreateEvent(
        Universe newUniverse
) {
}
