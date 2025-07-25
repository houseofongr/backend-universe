package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Sound.SoundID;

import java.util.UUID;

public record SoundDeleteEvent(
        SoundID deleteSoundID,
        UUID deleteAudioFileID
) {
}
