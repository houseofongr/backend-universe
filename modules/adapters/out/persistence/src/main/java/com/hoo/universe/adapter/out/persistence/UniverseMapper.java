package com.hoo.universe.adapter.out.persistence;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.adapter.out.persistence.entity.*;
import com.hoo.universe.adapter.out.persistence.entity.vo.CommonMetadataJpaValue;
import com.hoo.universe.adapter.out.persistence.entity.vo.PieceMetadataJpaValue;
import com.hoo.universe.adapter.out.persistence.entity.vo.SpaceMetadataJpaValue;
import com.hoo.universe.api.dto.result.UniverseListInfo;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UniverseMapper {

    private final OutlineSerializer outlineSerializer;

    public Universe mapToUniverseOnly(UniverseJpaEntity universeJpaEntity) {
        return Universe.create(
                new Universe.UniverseID(universeJpaEntity.getUuid()),
                mapToUniverseCategory(universeJpaEntity.getCategory()),
                mapToUniverseAuthor(universeJpaEntity),
                mapToUniverseMetadata(universeJpaEntity),
                mapToCommonMetadata(universeJpaEntity.getCommonMetadata())
        ).newUniverse();
    }

    public Piece mapToPiece(PieceJpaEntity pieceJpaEntity) {
        return Piece.loadPieceOnly(
                new Piece.PieceID(pieceJpaEntity.getUuid()),
                new Space.SpaceID(pieceJpaEntity.getParentSpaceId()),
                mapToPieceMetadata(pieceJpaEntity.getPieceMetadata()),
                mapToCommonMetadata(pieceJpaEntity.getCommonMetadata()),
                outlineSerializer.deserialize(pieceJpaEntity.getOutlinePoints())
        );
    }

    public Category mapToUniverseCategory(CategoryJpaEntity category) {
        return new Category(
                category.getUuid(),
                category.getTitleEng(),
                category.getTitleKor()
        );
    }

    public CommonMetadata mapToCommonMetadata(CommonMetadataJpaValue pieceJpaEntity) {
        return CommonMetadata.create(
                pieceJpaEntity.getTitle(),
                pieceJpaEntity.getDescription()
        );
    }

    public UniverseListInfo mapToUniverseInfo(UniverseJpaEntity universeJpaEntity) {
        return new UniverseListInfo(
                universeJpaEntity.getUuid(),
                universeJpaEntity.getUniverseMetadata().getThumbmusicFileID(),
                universeJpaEntity.getUniverseMetadata().getThumbnailFileID(),
                universeJpaEntity.getOwner().getOwnerId(),
                universeJpaEntity.getCommonMetadata().getCreatedTime().toEpochSecond(),
                universeJpaEntity.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                universeJpaEntity.getUniverseMetadata().getViewCount(),
                universeJpaEntity.getUniverseLikes().size(),
                universeJpaEntity.getCommonMetadata().getTitle(),
                universeJpaEntity.getCommonMetadata().getDescription(),
                universeJpaEntity.getOwner().getOwnerNickname(),
                universeJpaEntity.getUniverseMetadata().getAccessLevel().name(),
                mapToUniverseCategory(universeJpaEntity.getCategory()),
                mapToUniverseTag(universeJpaEntity.getUniverseHashtags())
        );
    }

    public OpenPieceResult mapToOpenPieceResult(PieceJpaEntity pieceJpaEntity, PageQueryResult<SoundJpaEntity> soundQueryResult) {
        return new OpenPieceResult(
                pieceJpaEntity.getUuid(),
                pieceJpaEntity.getCommonMetadata().getTitle(),
                pieceJpaEntity.getCommonMetadata().getDescription(),
                pieceJpaEntity.getPieceMetadata().getHidden(),
                pieceJpaEntity.getCommonMetadata().getCreatedTime().toEpochSecond(),
                pieceJpaEntity.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                soundQueryResult.map(this::mapToSoundInfo)
        );
    }

    private OpenPieceResult.SoundInfo mapToSoundInfo(SoundJpaEntity soundJpaEntity) {
        return new OpenPieceResult.SoundInfo(
                soundJpaEntity.getUuid(),
                soundJpaEntity.getSoundMetadata().getAudioFileID(),
                soundJpaEntity.getCommonMetadata().getTitle(),
                soundJpaEntity.getCommonMetadata().getDescription(),
                soundJpaEntity.getSoundMetadata().getHidden(),
                soundJpaEntity.getCommonMetadata().getCreatedTime().toEpochSecond(),
                soundJpaEntity.getCommonMetadata().getUpdatedTime().toEpochSecond()
        );
    }

    private UniverseMetadata mapToUniverseMetadata(UniverseJpaEntity universeJpaEntity) {
        return UniverseMetadata.create(
                universeJpaEntity.getUniverseMetadata().getThumbmusicFileID(),
                universeJpaEntity.getUniverseMetadata().getThumbnailFileID(),
                universeJpaEntity.getUniverseMetadata().getBackgroundFileID(),
                universeJpaEntity.getUniverseMetadata().getAccessLevel(),
                mapToUniverseTag(universeJpaEntity.getUniverseHashtags())
        );
    }

    public SpaceMetadata mapToSpaceMetadata(SpaceMetadataJpaValue spaceMetadata) {
        return SpaceMetadata.create(
                spaceMetadata.getBackgroundFileID(),
                spaceMetadata.getHidden());
    }

    public PieceMetadata mapToPieceMetadata(PieceMetadataJpaValue pieceMetadata) {
        return PieceMetadata.create(
                pieceMetadata.getImageFileID(),
                pieceMetadata.getHidden());
    }

    private Owner mapToUniverseAuthor(UniverseJpaEntity universeJpaEntity) {
        return new Owner(universeJpaEntity.getOwner().getOwnerId(), universeJpaEntity.getOwner().getOwnerNickname());
    }

    private List<String> mapToUniverseTag(List<UniverseTagJpaEntity> universeHashtagJpaEntities) {
        return universeHashtagJpaEntities.stream()
                .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                .toList();
    }
}
