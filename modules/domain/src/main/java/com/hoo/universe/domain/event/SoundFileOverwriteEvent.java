package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Sound.SoundID;

import java.util.UUID;

public record SoundFileOverwriteEvent(
        SoundID soundID,
        UUID oldAudioID,
        UUID newAudioID
) {
}
