package com.hoo.universe.api.out;

import com.hoo.universe.domain.event.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.SoundMetadataUpdateEvent;

public interface UpdateSoundStatusPort {

    void updateSoundMetadata(SoundMetadataUpdateEvent event);

    void updateSoundFileOverwrite(SoundFileOverwriteEvent event);

}
