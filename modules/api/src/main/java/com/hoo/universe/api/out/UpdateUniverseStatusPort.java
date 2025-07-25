package com.hoo.universe.api.out;

import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;

public interface UpdateUniverseStatusPort {

    void updateUniverseMetadata(UniverseMetadataUpdateEvent event);

    void updateUniverseFileOverwrite(UniverseFileOverwriteEvent event);
}
