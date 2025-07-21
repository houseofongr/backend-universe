package com.hoo.universe.adapter.out.persistence.query;

import com.hoo.universe.adapter.out.persistence.OutlineSerializer;
import com.hoo.universe.adapter.out.persistence.UniverseMapper;
import com.hoo.universe.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.application.exception.*;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.piece.PieceCreateEvent;
import com.hoo.universe.domain.event.sound.SoundCreateEvent;
import com.hoo.universe.domain.event.space.SpaceCreateEvent;
import com.hoo.universe.domain.vo.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class LoadUniverseAdapter implements LoadUniversePort {

    private final UniverseJpaRepository universeJpaRepository;
    private final UniverseMapper universeMapper;
    private final OutlineSerializer outlineSerializer;

    @Override
    public Universe loadUniverseOnly(UUID universeID) {

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(universeID).orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.UNIVERSE_NOT_FOUND));
        return universeMapper.mapToUniverseOnly(universeJpaEntity);
    }

    @Override
    public Universe loadUniverseExceptSounds(UUID universeID) {

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(universeID).orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.UNIVERSE_NOT_FOUND));
        Universe universe = universeMapper.mapToUniverseOnly(universeJpaEntity);

        for (SpaceJpaEntity spaceJpaEntity : universeJpaEntity.getSpaces()) {
            createSpace(universe, spaceJpaEntity);
        }

        for (PieceJpaEntity pieceJpaEntity : universeJpaEntity.getPieces()) {
            createPiece(universe, pieceJpaEntity);
        }

        return universe;
    }

    @Override
    public Universe loadUniverseWithAllEntity(UUID universeID) {

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(universeID).orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.UNIVERSE_NOT_FOUND));
        Universe universe = universeMapper.mapToUniverseOnly(universeJpaEntity);

        for (SpaceJpaEntity spaceJpaEntity : universeJpaEntity.getSpaces()) {
            createSpace(universe, spaceJpaEntity);
        }

        for (PieceJpaEntity pieceJpaEntity : universeJpaEntity.getPieces()) {
            Piece piece = createPiece(universe, pieceJpaEntity);
            createSound(piece, pieceJpaEntity.getSounds());
        }

        return universe;
    }

    private void createSpace(Universe universe, SpaceJpaEntity spaceJpaEntity) {
        SpaceCreateEvent event = universe.createSpaceInside(
                new Space.SpaceID(spaceJpaEntity.getUuid()),
                new Space.SpaceID(spaceJpaEntity.getParentSpaceId()),
                universeMapper.mapToSpaceMetadata(spaceJpaEntity.getSpaceMetadata()),
                universeMapper.mapToCommonMetadata(spaceJpaEntity.getCommonMetadata()),
                outlineSerializer.deserialize(spaceJpaEntity.getOutlinePoints())
        );

        if (event.maxChildExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_CHILD_EXCEED);
        if (event.maxSpaceExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_SPACE_EXCEED);
        if (event.overlapEvent().isOverlapped())
            throw new UniverseDomainException(DomainErrorCode.OVERLAPPED, DomainErrorCode.OVERLAPPED.getMessage() + event.overlapEvent().renderOverlapStatus());

    }

    private Piece createPiece(Universe universe, PieceJpaEntity pieceJpaEntity) {
        PieceCreateEvent event = universe.createPieceInside(
                new Piece.PieceID(pieceJpaEntity.getUuid()),
                new Space.SpaceID(pieceJpaEntity.getParentSpaceId()),
                universeMapper.mapToPieceMetadata(pieceJpaEntity.getPieceMetadata()),
                universeMapper.mapToCommonMetadata(pieceJpaEntity.getCommonMetadata()),
                outlineSerializer.deserialize(pieceJpaEntity.getOutlinePoints())

        );

        if (event.maxChildExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_CHILD_EXCEED);
        if (event.overlapEvent().isOverlapped())
            throw new UniverseDomainException(DomainErrorCode.OVERLAPPED, DomainErrorCode.OVERLAPPED.getMessage() + event.overlapEvent().renderOverlapStatus());

        return event.newPiece();
    }

    private void createSound(Piece piece, List<SoundJpaEntity> sounds) {
        for (SoundJpaEntity soundJpaEntity : sounds) {
            SoundCreateEvent event = piece.createSoundInside(
                    new Sound.SoundID(soundJpaEntity.getUuid()),
                    SoundMetadata.create(
                            soundJpaEntity.getSoundMetadata().getAudioFileID(),
                            soundJpaEntity.getSoundMetadata().getHidden()),
                    universeMapper.mapToCommonMetadata(soundJpaEntity.getCommonMetadata())
            );

            if (event.maxSoundExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_SOUND_EXCEED);
        }
    }
}
