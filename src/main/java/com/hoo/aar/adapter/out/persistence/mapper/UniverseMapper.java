package com.hoo.aar.adapter.out.persistence.mapper;

import com.hoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.admin.domain.universe.UniverseCategory;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.admin.domain.user.User;
import com.hoo.common.adapter.out.persistence.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AARUniverseMapper")
@RequiredArgsConstructor
public class UniverseMapper {

    public SearchPublicUniverseResult.UniverseListInfo mapToSearchPublicUniverseListInfo(Long userId, UniverseJpaEntity universeJpaEntity) {
        return new SearchPublicUniverseResult.UniverseListInfo(
                universeJpaEntity.getId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getAuthor().getId(),
                universeJpaEntity.getCreatedTime().toEpochSecond(),
                universeJpaEntity.getViewCount(),
                universeJpaEntity.getUniverseLikes().size(),
                userId == null ?
                        null :
                        universeJpaEntity.getUniverseLikes().stream()
                                .anyMatch(universeLikeJpaEntity -> universeLikeJpaEntity.getUser().getId().equals(userId)),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                universeJpaEntity.getAuthor().getNickname(),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                        .toList()
        );
    }

    public TraversalComponents mapToTraversalComponents(TraversalJpaEntityComponents traversalJpaEntityComponents) {

        UniverseJpaEntity universeJpaEntity = traversalJpaEntityComponents.universeJpaEntity();
        List<SpaceJpaEntity> spaceJpaEntities = traversalJpaEntityComponents.spaceJpaEntities();
        List<PieceJpaEntity> pieceJpaEntities = traversalJpaEntityComponents.pieceJpaEntities();

        Universe universe = this.mapToDomainEntity(universeJpaEntity);

        List<Space> spaces = spaceJpaEntities.stream().map(spaceJpaEntity -> Space.loadTreeComponent(
                spaceJpaEntity.getId(),
                spaceJpaEntity.getInnerImageFileId(),
                spaceJpaEntity.getUniverseId(),
                spaceJpaEntity.getParentSpaceId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getSx(),
                spaceJpaEntity.getSy(),
                spaceJpaEntity.getEx(),
                spaceJpaEntity.getEy(),
                spaceJpaEntity.getHidden(),
                spaceJpaEntity.getCreatedTime(),
                spaceJpaEntity.getUpdatedTime()
        )).toList();

        List<Piece> pieces = pieceJpaEntities.stream().map(pieceJpaEntity -> Piece.loadTreeComponent(
                pieceJpaEntity.getId(),
                pieceJpaEntity.getInnerImageFileId(),
                pieceJpaEntity.getUniverseId(),
                pieceJpaEntity.getParentSpaceId(),
                pieceJpaEntity.getTitle(),
                pieceJpaEntity.getDescription(),
                pieceJpaEntity.getSx(),
                pieceJpaEntity.getSy(),
                pieceJpaEntity.getEx(),
                pieceJpaEntity.getEy(),
                pieceJpaEntity.getHidden(),
                pieceJpaEntity.getCreatedTime(),
                pieceJpaEntity.getUpdatedTime()
        )).toList();

        return new TraversalComponents(universe, spaces, pieces);
    }

    public Universe mapToDomainEntity(UniverseJpaEntity universeJpaEntity) {
        CategoryJpaEntity category = universeJpaEntity.getCategory();

        return Universe.load(universeJpaEntity.getId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getInnerImageFileId(),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                new UniverseCategory(
                        category.getId(),
                        category.getTitleEng(),
                        category.getTitleKor()
                ),
                universeJpaEntity.getPublicStatus(),
                universeJpaEntity.getUniverseLikes().size(),
                universeJpaEntity.getViewCount(),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag()).toList(),
                User.load(universeJpaEntity.getAuthor().getId(), universeJpaEntity.getAuthor().getNickname()),
                universeJpaEntity.getCreatedTime(),
                universeJpaEntity.getUpdatedTime());
    }
}
