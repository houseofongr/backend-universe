package com.hoo.universe.api.out.persistence;

import com.hoo.universe.domain.event.space.*;

public interface HandleSpaceEventPort {
    void handleSpaceCreateEvent(SpaceCreateEvent event);

    void handleSpaceMetadataUpdateEvent(SpaceMetadataUpdateEvent event);

    void handleSpaceFileOverwriteEvent(SpaceFileOverwriteEvent event);

    void handleSpaceMoveEvent(SpaceMoveEvent event);

    void handleSpaceDeleteEvent(SpaceDeleteEvent event);
}
