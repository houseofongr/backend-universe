package com.hoo.universe.api.out;

import com.hoo.universe.domain.event.sound.SoundCreateEvent;
import com.hoo.universe.domain.event.sound.SoundDeleteEvent;
import com.hoo.universe.domain.event.sound.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.sound.SoundMetadataUpdateEvent;

public interface HandleSoundEventPort {
    void handleSoundCreateEvent(SoundCreateEvent event);

    void handleSoundMetadataUpdateEvent(SoundMetadataUpdateEvent event);

    void handleSoundFileOverwriteEvent(SoundFileOverwriteEvent event);

    void handleSoundDeleteEvent(SoundDeleteEvent event);
}
