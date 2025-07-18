package com.hoo.aar.application.port.out.persistence.universe;

import com.hoo.admin.domain.universe.TraversalComponents;

public interface ViewPublicUniversePort {
    TraversalComponents viewPublicUniverse(Long universeId);
}
