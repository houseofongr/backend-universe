package com.hoo.universe.api.out;

import com.hoo.universe.domain.event.UniverseCreateEvent;
import com.hoo.universe.domain.event.UniverseDeleteEvent;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;

public interface HandleUniverseEventPort {
    void handleCreateUniverseEvent(UniverseCreateEvent event);

    void handleUniverseMetadataUpdateEvent(UniverseMetadataUpdateEvent event);

    void handleUniverseFileOverwriteEvent(UniverseFileOverwriteEvent event);

    void handleUniverseDeleteEvent(UniverseDeleteEvent event);
}
