package com.hoo.universe.domain;

import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.event.*;
import com.hoo.universe.domain.event.piece.PieceCreateEvent;
import com.hoo.universe.domain.event.piece.PieceMoveEvent;
import com.hoo.universe.domain.event.space.SpaceCreateEvent;
import com.hoo.universe.domain.event.space.SpaceMoveEvent;
import com.hoo.universe.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Universe {

    public static final int MAX_SPACE_SIZE = 10;
    public static final int MAX_CHILD_SIZE = 100;

    private final UniverseID id;
    private Category category;
    private Owner owner;
    private UniverseMetadata universeMetadata;
    private CommonMetadata commonMetadata;
    private final List<Space> spaces;
    private final List<Piece> pieces;

    public static UniverseCreateEvent create(UniverseID universeID, Category category, Owner owner, UniverseMetadata universeMetadata, CommonMetadata commonMetadata) {
        return new UniverseCreateEvent(new Universe(universeID, category, owner, universeMetadata, commonMetadata, new ArrayList<>(), new ArrayList<>()));
    }

    public SpaceCreateEvent createSpaceInside(SpaceID spaceID, SpaceID parentSpaceID, SpaceMetadata spaceMetadata, CommonMetadata commonMetadata, Outline outline) {

        if (spaces.size() == MAX_SPACE_SIZE)
            return new SpaceCreateEvent(true, false, OverlapEvent.no(), null);

        if (pieces.size() + spaces.size() == MAX_CHILD_SIZE)
            return new SpaceCreateEvent(false, true, OverlapEvent.no(), null);

        OverlapEvent event = outline.overlap(getExistShapes());

        if (event.isOverlapped())
            return new SpaceCreateEvent(false, false, event, null);

        Space newSpace = Space.create(this, spaceID, parentSpaceID, spaceMetadata, commonMetadata, outline);

        if (parentSpaceID != null && parentSpaceID.uuid() != null)
            return getSpace(parentSpaceID).addSpaceInside(newSpace);

        spaces.add(newSpace);

        return new SpaceCreateEvent(false, false, OverlapEvent.no(), newSpace);
    }

    public PieceCreateEvent createPieceInside(PieceID pieceID, SpaceID parentSpaceID, PieceMetadata pieceMetadata, CommonMetadata commonMetadata, Outline outline) {

        if (pieces.size() + spaces.size() == MAX_CHILD_SIZE)
            return new PieceCreateEvent(true, OverlapEvent.no(), null);

        OverlapEvent event = outline.overlap(getExistShapes());

        if (event.isOverlapped()) {
            return new PieceCreateEvent(false, event, null);
        }

        Piece newPiece = Piece.create(this, pieceID, parentSpaceID, pieceMetadata, commonMetadata, outline);

        if (parentSpaceID != null && parentSpaceID.uuid() != null)
            return getSpace(parentSpaceID).addPieceInside(newPiece);

        pieces.add(newPiece);

        return new PieceCreateEvent(false, OverlapEvent.no(), newPiece);
    }

    public Space getSpace(SpaceID spaceID) {

        for (Space childSpace : spaces) {
            Space found = getSpace(childSpace, spaceID);
            if (found != null) return found;
        }

        throw new IllegalArgumentException("존재하지 않는 스페이스 : " + spaceID);
    }

    public List<Space> getAllSpaces() {
        List<Space> found = new ArrayList<>();
        Deque<Space> queue = new ArrayDeque<>(spaces);
        while (!queue.isEmpty()) {
            Space space = queue.poll();
            found.add(space);
            for (Space child : space.getSpaces()) queue.offer(child);
        }
        return found;
    }

    public Piece getPiece(PieceID pieceID) {

        for (Piece piece : pieces) {
            if (piece.getId().equals(pieceID)) return piece;
        }

        for (Space childSpace : spaces) {
            Piece found = getPiece(childSpace, pieceID);
            if (found != null) return found;
        }

        throw new IllegalArgumentException("존재하지 않는 피스 : " + pieceID);
    }

    public List<Piece> getAllPieces() {
        List<Piece> found = new ArrayList<>(pieces);
        Deque<Space> queue = new ArrayDeque<>(spaces);
        while (!queue.isEmpty()) {
            Space space = queue.poll();
            found.addAll(space.getPieces());
            for (Space child : space.getSpaces()) queue.offer(child);
        }
        return found;
    }

    public UniverseMetadataUpdateEvent updateMetadata(Category category, Owner owner, String title, String description, AccessLevel accessLevel, List<String> tags) {

        this.category = category;
        this.owner = owner;
        this.commonMetadata = commonMetadata.update(title, description);
        this.universeMetadata = universeMetadata.update(accessLevel, tags);

        return UniverseMetadataUpdateEvent.from(id, category, owner, commonMetadata, universeMetadata);
    }

    public UniverseFileOverwriteEvent overwriteFiles(UUID newThumbmusicID, UUID newThumbnailID, UUID newInnerImageID) {

        UUID oldThumbmusicID = this.universeMetadata.getThumbmusicID();
        UUID oldThumbnailID = this.universeMetadata.getThumbnailID();
        UUID oldInnerImageID = this.universeMetadata.getBackgroundID();
        this.universeMetadata = universeMetadata.overwrite(newThumbmusicID, newThumbnailID, newInnerImageID);

        return UniverseFileOverwriteEvent.from(id, oldThumbmusicID, oldThumbnailID, oldInnerImageID, universeMetadata);
    }

    public SpaceMoveEvent moveSpace(SpaceID spaceID, Outline newOutline) {
        Space target = getSpace(spaceID);
        List<Outline> existOutline = getExistShapesExcept(target);

        return target.move(existOutline, newOutline);
    }

    public PieceMoveEvent movePiece(PieceID pieceID, Outline newOutline) {
        Piece target = getPiece(pieceID);
        List<Outline> existOutlines = getExistShapesExcept(target);

        return target.move(existOutlines, newOutline);
    }

    public UniverseDeleteEvent delete() {

        List<Space> allSpaces = getAllSpaces();
        List<Piece> allPieces = getAllPieces();
        List<Sound> allSounds = allPieces.stream().flatMap(piece -> piece.getSounds().stream()).toList();

        List<UUID> allUniverseFileIDs = List.of(universeMetadata.getThumbmusicID(), universeMetadata.getThumbnailID(), universeMetadata.getBackgroundID());
        List<UUID> allSpaceFIleIDs = allSpaces.stream().map(space -> space.getSpaceMetadata().getBackgroundID()).toList();
        List<UUID> allPieceFIleIDs = allPieces.stream().map(piece -> piece.getPieceMetadata().getImageID()).filter(Objects::nonNull).toList();
        List<UUID> allSoundFIleIDs = allSounds.stream().map(sound -> sound.getSoundMetadata().getAudioID()).toList();

        List<UUID> allDeleteFileIDs = Stream.of(allUniverseFileIDs, allSpaceFIleIDs, allPieceFIleIDs, allSoundFIleIDs).flatMap(Collection::stream).toList();

        List<SpaceID> spaceIDs = allSpaces.stream().map(Space::getId).toList();
        List<PieceID> pieceIDs = allPieces.stream().map(Piece::getId).toList();
        List<SoundID> soundIDs = allPieces.stream()
                .flatMap(piece -> piece.getSounds().stream())
                .map(Sound::getId)
                .toList();

        return new UniverseDeleteEvent(this.id, spaceIDs, pieceIDs, soundIDs, allDeleteFileIDs);
    }

    private Space getSpace(Space space, SpaceID spaceID) {

        if (space.getId().equals(spaceID)) return space;

        for (Space childSpace : space.getSpaces()) {
            Space found = getSpace(childSpace, spaceID);
            if (found != null) return found;
        }

        return null;
    }

    private Piece getPiece(Space space, PieceID pieceID) {

        for (Piece piece : space.getPieces()) {
            if (piece.getId().equals(pieceID)) return piece;
        }

        for (Space childSpace : space.getSpaces()) {
            Piece found = getPiece(childSpace, pieceID);
            if (found != null) return found;
        }

        return null;
    }

    private List<Outline> getExistShapes() {
        return Stream.concat(
                        spaces.stream().map(Space::getOutline),
                        pieces.stream().map(Piece::getOutline))
                .toList();
    }

    private List<Outline> getExistShapesExcept(Space target) {
        if (target.isFirstNode())
            return Stream.concat(
                            spaces.stream()
                                    .filter(space -> !space.equals(target))
                                    .map(Space::getOutline),
                            pieces.stream().map(Piece::getOutline))
                    .toList();

        Space parent = getSpace(target.getParentSpaceID());
        return Stream.concat(
                        parent.getSpaces().stream()
                                .filter(space -> !space.equals(target))
                                .map(Space::getOutline),
                        parent.getPieces().stream().map(Piece::getOutline))
                .toList();
    }

    private List<Outline> getExistShapesExcept(Piece target) {
        if (target.isFirstNode())
            return Stream.concat(
                            spaces.stream().map(Space::getOutline),
                            pieces.stream()
                                    .filter(piece -> !piece.equals(target))
                                    .map(Piece::getOutline))
                    .toList();

        Space parent = getSpace(target.getParentSpaceID());
        return Stream.concat(
                        parent.getSpaces().stream()
                                .map(Space::getOutline),
                        parent.getPieces().stream()
                                .filter(piece -> !piece.equals(target))
                                .map(Piece::getOutline))
                .toList();
    }

    public int getDepth(SpaceID spaceID) {

        for (Space space : spaces) {
            int depth = getDepth(space, spaceID, 1);
            if (depth != -1) return depth;
        }

        return -1;
    }

    private int getDepth(Space space, SpaceID spaceID, int depth) {

        if (space.getId().equals(spaceID)) return depth;

        for (Space childSpace : space.getSpaces()) {
            int temp = getDepth(childSpace, spaceID, depth + 1);
            if (temp != -1) return temp;
        }

        return -1;
    }

    public int getDepth(PieceID pieceID) {

        for (Piece piece : pieces) {
            if (piece.getId().equals(pieceID)) return 1;
        }

        for (Space space : spaces) {
            int depth = getDepth(space, pieceID, 1);
            if (depth != -1) return depth;
        }

        return -1;
    }

    private int getDepth(Space space, PieceID pieceID, int depth) {

        for (Piece piece : space.getPieces()) {
            if (piece.getId().equals(pieceID)) return depth + 1;
        }

        for (Space childSpace : space.getSpaces()) {
            int temp = getDepth(childSpace, pieceID, depth + 1);
            if (temp != -1) return temp;
        }

        return -1;
    }

    public record UniverseID(UUID uuid) {
    }
}
