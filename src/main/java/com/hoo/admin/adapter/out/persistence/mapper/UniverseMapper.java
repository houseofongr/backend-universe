package com.hoo.admin.adapter.out.persistence.mapper;

import com.hoo.admin.application.port.in.category.CategoryInfo;
import com.hoo.admin.application.port.in.universe.CreateUniverseResult;
import com.hoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.admin.application.port.in.universe.UpdateUniverseResult;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.admin.domain.universe.UniverseCategory;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UniverseMapper {

    private final UserMapper userMapper;

    public CreateUniverseResult mapToCreateUniverseResult(UniverseJpaEntity universeJpaEntity) {
        return new CreateUniverseResult(
                String.format("[#%d]번 유니버스가 생성되었습니다.", universeJpaEntity.getId()),
                universeJpaEntity.getId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getInnerImageFileId(),
                universeJpaEntity.getAuthor().getId(),
                universeJpaEntity.getCreatedTime().toEpochSecond(),
                universeJpaEntity.getCategory().getId(),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                universeJpaEntity.getAuthor().getNickname(),
                universeJpaEntity.getPublicStatus().name(),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                        .toList()
        );
    }

    public Universe mapToDomainEntity(UniverseJpaEntity universeJpaEntity) {
        return Universe.load(universeJpaEntity.getId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getInnerImageFileId(),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                new UniverseCategory(
                        universeJpaEntity.getCategory().getId(),
                        universeJpaEntity.getCategory().getTitleKor(),
                        universeJpaEntity.getCategory().getTitleEng()
                ),
                universeJpaEntity.getPublicStatus(),
                universeJpaEntity.getUniverseLikes().size(),
                universeJpaEntity.getViewCount(),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag()).toList(),
                userMapper.mapToDomainEntity(universeJpaEntity.getAuthor()),
                universeJpaEntity.getCreatedTime(),
                universeJpaEntity.getUpdatedTime());
    }

    public TraversalComponents mapToTraversalComponent(UniverseJpaEntity universeJpaEntity, List<SpaceJpaEntity> spaceJpaEntities, List<PieceJpaEntity> pieceJpaEntities) {
        Universe universe = Universe.loadTreeComponent(
                universeJpaEntity.getId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getInnerImageFileId()
        );

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


    public SearchUniverseResult.UniverseListInfo mapToSearchUniverseListInfo(UniverseJpaEntity universeJpaEntity) {
        CategoryJpaEntity category = universeJpaEntity.getCategory();
        return new SearchUniverseResult.UniverseListInfo(
                universeJpaEntity.getId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getAuthor().getId(),
                universeJpaEntity.getCreatedTime().toEpochSecond(),
                universeJpaEntity.getUpdatedTime().toEpochSecond(),
                universeJpaEntity.getViewCount(),
                universeJpaEntity.getUniverseLikes().size(),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                universeJpaEntity.getAuthor().getNickname(),
                universeJpaEntity.getPublicStatus().name(),
                mapToCategoryInfo(category),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                        .toList()
        );
    }

    public SearchUniverseResult.UniverseDetailInfo mapToSearchUniverseDetailInfo(UniverseJpaEntity universeJpaEntity) {
        return new SearchUniverseResult.UniverseDetailInfo(
                universeJpaEntity.getId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getInnerImageFileId(),
                universeJpaEntity.getAuthor().getId(),
                universeJpaEntity.getCreatedTime().toEpochSecond(),
                universeJpaEntity.getUpdatedTime().toEpochSecond(),
                universeJpaEntity.getViewCount(),
                universeJpaEntity.getUniverseLikes().size(),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                universeJpaEntity.getAuthor().getNickname(),
                universeJpaEntity.getPublicStatus().name(),
                mapToCategoryInfo(universeJpaEntity.getCategory()),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                        .toList()
        );
    }

    public UpdateUniverseResult.Detail mapToUpdateUniverseDetailResult(UniverseJpaEntity universeJpaEntity) {
        return new UpdateUniverseResult.Detail(
                String.format("[#%d]번 유니버스의 상세정보가 수정되었습니다.", universeJpaEntity.getId()),
                universeJpaEntity.getAuthor().getId(),
                universeJpaEntity.getUpdatedTime().toEpochSecond(),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                universeJpaEntity.getAuthor().getNickname(),
                universeJpaEntity.getPublicStatus().name(),
                mapToCategoryInfo(universeJpaEntity.getCategory()),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                        .toList()
        );
    }

    private CategoryInfo mapToCategoryInfo(CategoryJpaEntity category) {
        return new CategoryInfo(
                category.getId(),
                category.getTitleEng(),
                category.getTitleKor());
    }
}
