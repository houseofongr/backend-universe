package com.hoo.universe.domain;

import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.event.*;
import com.hoo.universe.domain.event.PieceCreateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Outline;
import com.hoo.universe.domain.vo.SpaceMetadata;
import lombok.*;

import java.util.*;
import java.util.stream.Stream;

import static com.hoo.universe.domain.Universe.*;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Space {

    private final SpaceID id;
    private final UniverseID universeID;
    private final SpaceID parentSpaceID;
    private SpaceMetadata spaceMetadata;
    private CommonMetadata commonMetadata;
    private Outline outline;
    private final List<Space> spaces;
    private final List<Piece> pieces;

    static Space create(Universe universe, SpaceID spaceID, SpaceID parentSpaceID, SpaceMetadata spaceMetadata, CommonMetadata commonMetadata, Outline outline) {
        return new Space(spaceID, universe.getId(), parentSpaceID, spaceMetadata, commonMetadata, outline, new ArrayList<>(), new ArrayList<>());
    }

    public SpaceMetadataUpdateEvent updateMetadata(String title, String description, Boolean hidden) {

        this.commonMetadata = commonMetadata.update(title, description);
        this.spaceMetadata = spaceMetadata.update(hidden);

        return SpaceMetadataUpdateEvent.from(id, commonMetadata, spaceMetadata);
    }

    public SpaceFileOverwriteEvent overwriteFile(UUID newInnerImageID) {

        UUID oldInnerImageID = this.spaceMetadata.getBackgroundID();
        this.spaceMetadata = spaceMetadata.overwrite(newInnerImageID);

        return new SpaceFileOverwriteEvent(id, oldInnerImageID, spaceMetadata.getBackgroundID());
    }

    public SpaceMoveEvent move(List<Outline> existOutlines, Outline newOutline) {

        OverlapEvent event = newOutline.overlap(existOutlines);
        if (event.isOverlapped())
            return new SpaceMoveEvent(universeID, id, event, null);

        this.outline = newOutline;

        return new SpaceMoveEvent(universeID, id, OverlapEvent.no(), newOutline);
    }

    public SpaceDeleteEvent delete() {

        List<Space> allSpaces = getAllSpaces();
        List<Piece> allPieces = getAllPieces();
        List<Sound> allSounds = allPieces.stream().flatMap(piece -> piece.getSounds().stream()).toList();

        List<UUID> allSpaceFIleIDs = allSpaces.stream().map(space -> space.getSpaceMetadata().getBackgroundID()).toList();
        List<UUID> allPieceFIleIDs = allPieces.stream().map(piece -> piece.getPieceMetadata().getImageID()).filter(Objects::nonNull).toList();
        List<UUID> allSoundFIleIDs = allSounds.stream().map(sound -> sound.getSoundMetadata().getAudioID()).toList();
        List<UUID> allDeleteFileIDs = Stream.of(allSpaceFIleIDs, allPieceFIleIDs, allSoundFIleIDs).flatMap(Collection::stream).toList();

        List<SpaceID> spaceIDs = allSpaces.stream().map(Space::getId).toList();
        List<PieceID> pieceIDs = allPieces.stream().map(Piece::getId).toList();
        List<SoundID> soundIDs = allPieces.stream()
                .flatMap(piece -> piece.getSounds().stream())
                .map(Sound::getId)
                .toList();

        return new SpaceDeleteEvent(spaceIDs, pieceIDs, soundIDs, allDeleteFileIDs);
    }

    public boolean isFirstNode() {
        return this.parentSpaceID == null || this.parentSpaceID.uuid == null;
    }

    SpaceCreateEvent addSpaceInside(Space newSpace) {

        if (spaces.size() == MAX_SPACE_SIZE)
            return new SpaceCreateEvent(true, false, OverlapEvent.no(), null);

        if (pieces.size() + spaces.size() == MAX_CHILD_SIZE)
            return new SpaceCreateEvent(false, true, OverlapEvent.no(), null);

        OverlapEvent event = newSpace.getOutline().overlap(getAllShapes());

        if (event.isOverlapped()) {
            return new SpaceCreateEvent(false, false, event, null);
        }

        spaces.add(newSpace);

        return new SpaceCreateEvent(false, false, OverlapEvent.no(), newSpace);
    }

    PieceCreateEvent addPieceInside(Piece newPiece) {

        if (pieces.size() + spaces.size() == MAX_CHILD_SIZE)
            return new PieceCreateEvent(true, OverlapEvent.no(), null);

        OverlapEvent event = newPiece.getOutline().overlap(getAllShapes());

        if (event.isOverlapped()) {
            return new PieceCreateEvent(false, event, null);
        }

        pieces.add(newPiece);

        return new PieceCreateEvent(false, OverlapEvent.no(), newPiece);
    }

    private List<Space> getAllSpaces() {

        List<Space> found = new ArrayList<>();
        found.add(this);
        Deque<Space> queue = new ArrayDeque<>(spaces);

        while (!queue.isEmpty()) {
            Space space = queue.poll();
            found.add(space);
            for (Space child : space.getSpaces()) queue.offer(child);
        }

        return found;
    }

    private List<Piece> getAllPieces() {

        List<Piece> found = new ArrayList<>(pieces);
        Deque<Space> queue = new ArrayDeque<>(spaces);

        while (!queue.isEmpty()) {
            Space space = queue.poll();
            found.addAll(space.getPieces());
            for (Space child : space.getSpaces()) queue.offer(child);
        }
        return found;
    }

    private List<Outline> getAllShapes() {
        return Stream.concat(
                        spaces.stream().map(space -> space.outline),
                        pieces.stream().map(Piece::getOutline))
                .toList();
    }

    public record SpaceID(UUID uuid) {
    }
}
