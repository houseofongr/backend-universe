package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SoundMetadata;

import java.time.ZonedDateTime;

public record SoundMetadataUpdateEvent(
        Sound.SoundID soundID,
        String title,
        String description,
        ZonedDateTime updatedTime,
        Boolean hidden
) {
    public static SoundMetadataUpdateEvent from(Sound.SoundID soundID, CommonMetadata commonMetadata, SoundMetadata soundMetadata) {
        return new SoundMetadataUpdateEvent(
                soundID,
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                soundMetadata.isHidden()
        );
    }
}
