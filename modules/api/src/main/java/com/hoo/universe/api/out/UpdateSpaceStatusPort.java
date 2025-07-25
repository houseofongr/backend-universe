package com.hoo.universe.api.out;

import com.hoo.universe.domain.event.SpaceFileOverwriteEvent;
import com.hoo.universe.domain.event.SpaceMetadataUpdateEvent;
import com.hoo.universe.domain.event.SpaceMoveEvent;

public interface UpdateSpaceStatusPort {

    void updateSpaceMetadata(SpaceMetadataUpdateEvent event);

    void updateSpaceFileOverwrite(SpaceFileOverwriteEvent event);

    void updateSpaceMove(SpaceMoveEvent event);
}
