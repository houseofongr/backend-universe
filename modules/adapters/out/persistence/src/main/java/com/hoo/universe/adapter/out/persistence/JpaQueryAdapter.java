package com.hoo.universe.adapter.out.persistence;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.out.persistence.entity.*;
import com.hoo.universe.adapter.out.persistence.repository.CategoryJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.api.out.QueryUniversePort;
import com.hoo.universe.api.out.dto.OpenPieceQueryResult;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.PieceCreateEvent;
import com.hoo.universe.domain.event.SoundCreateEvent;
import com.hoo.universe.domain.event.SpaceCreateEvent;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.SoundMetadata;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class JpaQueryAdapter implements
        LoadUniversePort,
        QueryUniversePort,
        QueryCategoryPort
{

    private final UniverseJpaRepository universeJpaRepository;
    private final PieceJpaRepository pieceJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final PersistenceMapper persistenceMapper;
    private final OutlineSerializer outlineSerializer;

    @Override
    public Universe loadUniverseOnly(UUID universeID) {

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(universeID).orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.UNIVERSE_NOT_FOUND));
        return persistenceMapper.mapToUniverseOnly(universeJpaEntity);
    }

    @Override
    public Universe loadUniverseExceptSounds(UUID universeID) {

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(universeID).orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.UNIVERSE_NOT_FOUND));
        Universe universe = persistenceMapper.mapToUniverseOnly(universeJpaEntity);

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
        Universe universe = persistenceMapper.mapToUniverseOnly(universeJpaEntity);

        for (SpaceJpaEntity spaceJpaEntity : universeJpaEntity.getSpaces()) {
            createSpace(universe, spaceJpaEntity);
        }

        for (PieceJpaEntity pieceJpaEntity : universeJpaEntity.getPieces()) {
            Piece piece = createPiece(universe, pieceJpaEntity);
            createSound(piece, pieceJpaEntity.getSounds());
        }

        return universe;
    }

    @Override
    public PageQueryResult<UniverseListQueryInfo> searchUniverses(SearchUniverseCommand command) {
        return universeJpaRepository.searchUniverse(command.categoryID(), command.pageRequest()).map(persistenceMapper::mapToUniverseInfo);
    }

    @Override
    public OpenPieceQueryResult searchPiece(UUID pieceID, PageRequest pageRequest) {

        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(pieceID)
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.PIECE_NOT_FOUND));

        PageQueryResult<SoundJpaEntity> soundQueryResult = pieceJpaRepository.searchAllSoundsById(pieceID, pageRequest);

        return persistenceMapper.mapToOpenPieceResult(pieceJpaEntity, soundQueryResult);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryJpaRepository.findAll().stream().map(persistenceMapper::mapToUniverseCategory).toList();
    }

    @Override
    public Category findUniverseCategory(UUID categoryId) {
        CategoryJpaEntity category = categoryJpaRepository.findByUuid(categoryId)
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.CATEGORY_NOT_FOUND));

        return persistenceMapper.mapToUniverseCategory(category);
    }
    private void createSpace(Universe universe, SpaceJpaEntity spaceJpaEntity) {
        SpaceCreateEvent event = universe.createSpaceInside(
                new Space.SpaceID(spaceJpaEntity.getUuid()),
                new Space.SpaceID(spaceJpaEntity.getParentSpaceId()),
                persistenceMapper.mapToSpaceMetadata(spaceJpaEntity.getSpaceMetadata()),
                persistenceMapper.mapToCommonMetadata(spaceJpaEntity.getCommonMetadata()),
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
                persistenceMapper.mapToPieceMetadata(pieceJpaEntity.getPieceMetadata()),
                persistenceMapper.mapToCommonMetadata(pieceJpaEntity.getCommonMetadata()),
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
                    persistenceMapper.mapToCommonMetadata(soundJpaEntity.getCommonMetadata())
            );

            if (event.maxSoundExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_SOUND_EXCEED);
        }
    }
}
