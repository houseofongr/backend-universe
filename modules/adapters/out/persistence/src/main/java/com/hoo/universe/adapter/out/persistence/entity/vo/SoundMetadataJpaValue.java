package com.hoo.universe.adapter.out.persistence.entity.vo;

import com.hoo.universe.domain.event.sound.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.sound.SoundMetadataUpdateEvent;
import com.hoo.universe.domain.vo.SoundMetadata;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.getOrDefault;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SoundMetadataJpaValue {

    @Column(columnDefinition = "BINARY(16)", name = "AUDIO_FILE_ID")
    private UUID audioFileID;

    @Column(nullable = false)
    private Boolean hidden;

    public static SoundMetadataJpaValue from(SoundMetadata soundMetadata) {
        return new SoundMetadataJpaValue(
                soundMetadata.getAudioID(),
                soundMetadata.isHidden()
        );
    }

    public void applyEvent(SoundMetadataUpdateEvent event) {
        this.hidden = getOrDefault(event.hidden(), hidden);
    }

    public void applyEvent(SoundFileOverwriteEvent event) {
        this.audioFileID = event.newAudioID();
    }

}
