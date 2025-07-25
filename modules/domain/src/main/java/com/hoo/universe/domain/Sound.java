package com.hoo.universe.domain;

import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.event.SoundDeleteEvent;
import com.hoo.universe.domain.event.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.SoundMetadataUpdateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SoundMetadata;
import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Sound {

    private final SoundID id;
    private final PieceID parentPieceID;
    private SoundMetadata soundMetadata;
    private CommonMetadata commonMetadata;

    static Sound create(Piece piece, SoundID soundID, SoundMetadata soundMetadata, CommonMetadata commonMetadata) {
        return new Sound(soundID, piece.getId(), soundMetadata, commonMetadata);
    }

    public SoundMetadataUpdateEvent updateMetadata(String title, String description, Boolean hidden) {

        this.commonMetadata = commonMetadata.update(title, description);
        this.soundMetadata = soundMetadata.update(hidden);

        return SoundMetadataUpdateEvent.from(id, commonMetadata, soundMetadata);
    }

    public SoundFileOverwriteEvent overwriteFile(UUID newAudioID) {

        UUID oldAudioID = this.soundMetadata.getAudioID();
        this.soundMetadata = soundMetadata.overwrite(newAudioID);

        return new SoundFileOverwriteEvent(id, oldAudioID, soundMetadata.getAudioID());
    }

    public SoundDeleteEvent delete() {
        return new SoundDeleteEvent(id, soundMetadata.getAudioID());
    }

    public record SoundID(UUID uuid) {
    }
}
