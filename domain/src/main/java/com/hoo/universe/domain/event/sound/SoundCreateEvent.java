package com.hoo.universe.domain.event.sound;

import com.hoo.universe.domain.Sound;

public record SoundCreateEvent(
        boolean maxSoundExceeded,
        Sound newSound
) {
}
