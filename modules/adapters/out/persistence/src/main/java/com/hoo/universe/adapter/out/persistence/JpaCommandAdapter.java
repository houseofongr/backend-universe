package com.hoo.universe.adapter.out.persistence;

import com.hoo.universe.adapter.out.persistence.entity.*;
import com.hoo.universe.adapter.out.persistence.repository.*;
import com.hoo.universe.api.out.*;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class JpaCommandAdapter implements
        SaveEntityPort,
        UpdateUniverseStatusPort,
        UpdateSpaceStatusPort,
        UpdatePieceStatusPort,
        UpdateSoundStatusPort,
        UpdateCategoryPort,
        DeleteEntityPort {

    private final UniverseJpaRepository universeJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    private final PieceJpaRepository pieceJpaRepository;
    private final SoundJpaRepository soundJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final OutlineSerializer outlineSerializer;


    @Override
    public void saveUniverse(Universe universe) {

        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findByUuid(universe.getCategory().getId())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.CATEGORY_NOT_FOUND));

        UniverseJpaEntity universeJpaEntity = UniverseJpaEntity.createNewEntity(universe, categoryJpaEntity);
        addUniverseTagJpaEntities(universeJpaEntity, universe.getUniverseMetadata().getTags());

        universeJpaRepository.save(universeJpaEntity);
    }

    @Override
    public void saveSpace(Space space) {
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(space.getUniverseID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(space.getOutline());
        SpaceJpaEntity spaceJpaEntity = SpaceJpaEntity.createNewEntity(space, universeJpaEntity, outlinePoints);

        spaceJpaRepository.save(spaceJpaEntity);
    }

    @Override
    public void savePiece(Piece piece) {
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(piece.getUniverseID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(piece.getOutline());
        PieceJpaEntity pieceJpaEntity = PieceJpaEntity.createNewEntity(piece, universeJpaEntity, outlinePoints);

        pieceJpaRepository.save(pieceJpaEntity);
    }

    @Override
    public void saveSound(Sound sound) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(sound.getParentPieceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        SoundJpaEntity soundJpaEntity = SoundJpaEntity.createNewEntity(sound, pieceJpaEntity);

        soundJpaRepository.save(soundJpaEntity);
    }

    @Override
    public void saveCategory(UUID uuid, String kor, String eng) {
        CategoryJpaEntity newCategory = CategoryJpaEntity.create(uuid, kor, eng);
        categoryJpaRepository.save(newCategory);
    }

    @Override
    public void updateUniverseMetadata(UniverseMetadataUpdateEvent event) {
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(event.universeID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        CategoryJpaEntity categoryJpaEntity = event.category() != null ?
                categoryJpaRepository.findByUuid(event.category().getId())
                        .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID))
                : null;

        universeJpaEntity.applyEvent(event, categoryJpaEntity);
    }

    @Override
    public void updateSpaceMetadata(SpaceMetadataUpdateEvent event) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        spaceJpaEntity.applyEvent(event);
    }

    @Override
    public void updatePieceMetadata(PieceMetadataUpdateEvent event) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(event.pieceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        pieceJpaEntity.applyEvent(event);
    }

    @Override
    public void updateSoundMetadata(SoundMetadataUpdateEvent event) {
        SoundJpaEntity soundJpaEntity = soundJpaRepository.findByUuid(event.soundID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        soundJpaEntity.applyEvent(event);
    }

    @Override
    public void updateUniverseFileOverwrite(UniverseFileOverwriteEvent event) {
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(event.universeID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        universeJpaEntity.applyEvent(event);
    }

    @Override
    public void updateSpaceFileOverwrite(SpaceFileOverwriteEvent event) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        spaceJpaEntity.applyEvent(event);
    }

    @Override
    public void updateSoundFileOverwrite(SoundFileOverwriteEvent event) {
        SoundJpaEntity soundJpaEntity = soundJpaRepository.findByUuid(event.soundID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        soundJpaEntity.applyEvent(event);
    }

    @Override
    public void updatePieceMove(PieceMoveEvent event) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(event.pieceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(event.outline());

        pieceJpaEntity.move(outlinePoints);
    }

    @Override
    public void updateSpaceMove(SpaceMoveEvent event) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(event.outline());

        spaceJpaEntity.move(outlinePoints);
    }

    @Override
    public void updateCategory(UUID categoryID, String kor, String eng) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findByUuid(categoryID)
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.CATEGORY_NOT_FOUND));

        categoryJpaEntity.update(kor, eng);
    }

    @Override
    public void deleteUniverse(UniverseDeleteEvent event) {
        universeJpaRepository.deleteByUuid(event.deleteUniverseID().uuid());
    }

    @Override
    public void deleteSpace(SpaceDeleteEvent event) {
        spaceJpaRepository.deleteAllByUuidIn(event.deleteSpaceIDs().stream().map(Space.SpaceID::uuid).toList());
        pieceJpaRepository.deleteAllByUuidIn(event.deletePieceIDs().stream().map(Piece.PieceID::uuid).toList());
        soundJpaRepository.deleteAllByUuidIn(event.deleteSoundIDs().stream().map(Sound.SoundID::uuid).toList());
    }

    @Override
    public void deletePiece(PieceDeleteEvent event) {
        pieceJpaRepository.deleteByUuid(event.deletePieceID().uuid());
        soundJpaRepository.deleteAllByUuidIn(event.deleteSoundIDs().stream().map(Sound.SoundID::uuid).toList());
    }

    @Override
    public void deleteSound(SoundDeleteEvent event) {
        soundJpaRepository.deleteByUuid(event.deleteSoundID().uuid());
    }

    @Override
    public void deleteCategory(UUID categoryID) {
        categoryJpaRepository.deleteByUuid(categoryID);
    }

    private void addUniverseTagJpaEntities(UniverseJpaEntity universeJpaEntity, List<String> tags) {
        List<String> existTags = tagJpaRepository.findExistTags(tags);
        List<String> notExistTags = tags.stream()
                .filter(tag -> !existTags.contains(tag))
                .toList();

        List<TagJpaEntity> newTagJpaEntities = createTags(notExistTags);
        List<UniverseTagJpaEntity> universeTagJpaEntities = newTagJpaEntities.stream()
                .map(tagJpaEntity -> UniverseTagJpaEntity.createNewEntity(universeJpaEntity, tagJpaEntity))
                .toList();

        universeJpaEntity.getUniverseHashtags().addAll(universeTagJpaEntities);
    }
    private List<TagJpaEntity> createTags(List<String> notExistTags) {
        List<TagJpaEntity> newTagJpaEntities = notExistTags.stream().map(TagJpaEntity::createNewEntity).toList();
        tagJpaRepository.saveAll(newTagJpaEntities);
        return newTagJpaEntities;
    }

}
