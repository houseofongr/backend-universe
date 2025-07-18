package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SoundMetadata {

    private final UUID audioID;
    private final boolean hidden;

    public static SoundMetadata create(UUID audioID, Boolean hidden) {
        return new SoundMetadata(audioID, orElseFalse(hidden));
    }

    public SoundMetadata update(Boolean hidden) {
        return new SoundMetadata(audioID, getOrDefault(hidden, this.hidden));
    }

    public SoundMetadata overwrite(UUID newAudioID) {
        return new SoundMetadata(newAudioID, hidden);
    }

}
