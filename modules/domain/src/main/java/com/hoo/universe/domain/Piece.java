package com.hoo.universe.domain;

import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.event.OverlapEvent;
import com.hoo.universe.domain.event.PieceDeleteEvent;
import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.PieceMoveEvent;
import com.hoo.universe.domain.event.SoundCreateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;
import com.hoo.universe.domain.vo.Outline;
import com.hoo.universe.domain.vo.SoundMetadata;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Piece {

    public static final int MAX_SOUND_SIZE = 100;

    private final PieceID id;
    private final UniverseID universeID;
    private final SpaceID parentSpaceID;
    private PieceMetadata pieceMetadata;
    private CommonMetadata commonMetadata;
    private Outline outline;
    private final List<Sound> sounds;

    public static Piece loadPieceOnly(PieceID pieceID, SpaceID parentSpaceID, PieceMetadata pieceMetadata, CommonMetadata commonMetadata, Outline outline) {
        return new Piece(pieceID, null, parentSpaceID, pieceMetadata, commonMetadata, outline, List.of());
    }

    static Piece create(Universe universe, PieceID pieceID, SpaceID parentSpaceID, PieceMetadata pieceMetadata, CommonMetadata commonMetadata, Outline outline) {
        return new Piece(pieceID, universe.getId(), parentSpaceID, pieceMetadata, commonMetadata, outline, new ArrayList<>());
    }

    public PieceMetadataUpdateEvent updateMetadata(String title, String description, Boolean hidden) {

        this.commonMetadata = commonMetadata.update(title, description);
        this.pieceMetadata = pieceMetadata.update(hidden);

        return PieceMetadataUpdateEvent.from(id, commonMetadata, pieceMetadata);
    }

    public PieceMoveEvent move(List<Outline> existOutlines, Outline newOutline) {

        OverlapEvent event = newOutline.overlap(existOutlines);

        if (event.isOverlapped())
            return new PieceMoveEvent(universeID, id, event, null);

        this.outline = newOutline;

        return new PieceMoveEvent(universeID, id, OverlapEvent.no(), newOutline);
    }

    public PieceDeleteEvent delete() {

        List<SoundID> soundIDs = sounds.stream().map(Sound::getId).toList();
        List<UUID> deleteFileIDs = Stream.concat(
                        sounds.stream().map(sound -> sound.getSoundMetadata().getAudioID()),
                        Stream.ofNullable(pieceMetadata.getImageID()))
                .toList();

        return new PieceDeleteEvent(
                this.id,
                soundIDs,
                deleteFileIDs
        );
    }

    public SoundCreateEvent createSoundInside(SoundID soundID, SoundMetadata soundMetadata, CommonMetadata commonMetadata) {

        if (sounds.size() == MAX_SOUND_SIZE)
            return new SoundCreateEvent(true, null);

        Sound newSound = Sound.create(this, soundID, soundMetadata, commonMetadata);

        sounds.add(newSound);

        return new SoundCreateEvent(false, newSound);
    }

    public Sound getSound(SoundID soundID) {
        return sounds.stream().filter(sound -> sound.getId().equals(soundID)).findFirst().orElse(null);
    }

    public boolean isFirstNode() {
        return this.parentSpaceID == null || this.parentSpaceID.uuid() == null;
    }

    public record PieceID(UUID uuid) {
    }
}
