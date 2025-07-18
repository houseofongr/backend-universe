package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SoundMetadata {

    private final UUID audioID;
    private final boolean hidden;

    public static SoundMetadata create(UUID audioID, Boolean hidden) {
        return new SoundMetadata(audioID, orElseNotHidden(hidden));
    }

    public SoundMetadata update(Boolean hidden) {
        return new SoundMetadata(audioID, getOrDefault(hidden, this.hidden));
    }

    public SoundMetadata overwrite(UUID newAudioID) {
        return new SoundMetadata(newAudioID, hidden);
    }

    private static boolean orElseNotHidden(Boolean hidden) {
        return hidden != null && hidden;
    }

    private  <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
