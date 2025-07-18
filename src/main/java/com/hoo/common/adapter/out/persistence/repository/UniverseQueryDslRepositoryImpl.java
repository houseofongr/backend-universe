package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import com.hoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.PublicStatus;
import com.hoo.common.adapter.out.persistence.condition.UniverseSearchType;
import com.hoo.common.adapter.out.persistence.condition.UniverseSortType;
import com.hoo.common.adapter.out.persistence.entity.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.hoo.common.adapter.out.persistence.entity.QPieceJpaEntity.pieceJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QSpaceJpaEntity.spaceJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QUniverseHashtagJpaEntity.universeHashtagJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QUniverseJpaEntity.universeJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QUniverseLikeJpaEntity.universeLikeJpaEntity;

public class UniverseQueryDslRepositoryImpl implements UniverseQueryDslRepository {

    private final JPAQueryFactory query;

    public UniverseQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<UniverseJpaEntity> searchAll(SearchUniverseCommand command) {
        List<UniverseJpaEntity> entities = query.selectFrom(universeJpaEntity)
                .leftJoin(universeJpaEntity.universeHashtags, universeHashtagJpaEntity).fetchJoin()
                .where(search(command.keyword(), command.searchType()))
                .where(filter(command))
                .orderBy(order(command.sortType(), command.isAsc()))
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(universeJpaEntity.count())
                .from(universeJpaEntity)
                .where(search(command.keyword(), command.searchType()))
                .where(filter(command))
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count == null ? 0 : count);
    }

    @Override
    public TraversalJpaEntityComponents findAllTreeComponentById(Long universeId) {
        UniverseJpaEntity universe = query
                .selectFrom(universeJpaEntity)
                .where(universeJpaEntity.id.eq(universeId))
                .stream().findFirst()
                .orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        List<SpaceJpaEntity> spaceJpaEntities = query
                .selectFrom(spaceJpaEntity)
                .where(spaceJpaEntity.universeId.eq(universeId))
                .fetch();

        List<PieceJpaEntity> pieceJpaEntities = query
                .selectFrom(pieceJpaEntity)
                .where(pieceJpaEntity.universeId.eq(universeId))
                .fetch();

        return new TraversalJpaEntityComponents(universe, spaceJpaEntities, pieceJpaEntities);
    }

    @Override
    public TraversalJpaEntityComponents findAllPublicTreeComponentById(Long universeId) {
        UniverseJpaEntity universe = query
                .selectFrom(universeJpaEntity)
                .where(universeJpaEntity.id.eq(universeId))
                .stream().findFirst()
                .orElseThrow(() -> new AarException(AarErrorCode.UNIVERSE_NOT_FOUND));

        List<SpaceJpaEntity> spaceJpaEntities = query
                .selectFrom(spaceJpaEntity)
                .where(spaceJpaEntity.universeId.eq(universeId)
                        .and(spaceJpaEntity.hidden.eq(false)))
                .fetch();

        List<PieceJpaEntity> pieceJpaEntities = query
                .selectFrom(pieceJpaEntity)
                .where(pieceJpaEntity.universeId.eq(universeId)
                        .and(pieceJpaEntity.hidden.eq(false)))
                .fetch();

        return new TraversalJpaEntityComponents(universe, spaceJpaEntities, pieceJpaEntities);
    }

    @Override
    public boolean checkIsLiked(Long universeId, Long userId) {
        return query.selectFrom(universeLikeJpaEntity)
                .where(universeLikeJpaEntity.universe.id.eq(universeId)
                        .and(universeLikeJpaEntity.user.id.eq(userId))
                ).stream().findFirst().isPresent();
    }

    @Override
    public  Page<UniverseJpaEntity>  searchAllPublic(SearchPublicUniverseCommand command) {
        List<UniverseJpaEntity> entities = query.selectFrom(universeJpaEntity)
                .leftJoin(universeJpaEntity.universeHashtags, universeHashtagJpaEntity).fetchJoin()
                .where(universeJpaEntity.publicStatus.eq(PublicStatus.PUBLIC))
                .where(search(command.keyword(), command.searchType()))
                .orderBy(order(command.sortType(), command.isAsc()))
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(universeJpaEntity.count())
                .from(universeJpaEntity)
                .where(universeJpaEntity.publicStatus.eq(PublicStatus.PUBLIC))
                .where(search(command.keyword(), command.searchType()))
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count == null ? 0 : count);
    }

    private BooleanExpression filter(SearchUniverseCommand command) {
        if (command.categoryId() == null)
            return null;

        return universeJpaEntity.category.id.eq(command.categoryId());
    }

    private BooleanExpression search(String keyword, String searchType) {
        if (keyword == null || keyword.isBlank() || !UniverseSearchType.contains(searchType))
            return null;

        return switch (UniverseSearchType.valueOf(searchType.toUpperCase())) {
            case CONTENT -> universeJpaEntity.title.likeIgnoreCase("%" + keyword + "%")
                    .or(universeJpaEntity.description.likeIgnoreCase("%" + keyword + "%"));
            case AUTHOR -> universeJpaEntity.owner.nickname.likeIgnoreCase("%" + keyword + "%");
            case ALL -> universeJpaEntity.title.likeIgnoreCase("%" + keyword + "%")
                    .or(universeJpaEntity.description.likeIgnoreCase("%" + keyword + "%"))
                    .or(universeJpaEntity.owner.nickname.likeIgnoreCase("%" + keyword + "%"));
        };
    }

    private OrderSpecifier<?> order(String sortType, Boolean isAsc) {
        if (isAsc == null || sortType == null || !UniverseSortType.contains(sortType))
            return new OrderSpecifier<>(Order.DESC, universeJpaEntity.createdTime);

        Order order = isAsc ? Order.ASC : Order.DESC;
        return switch (UniverseSortType.valueOf(sortType.toUpperCase())) {
            case TITLE -> new OrderSpecifier<>(order, universeJpaEntity.title);
            case REGISTERED_DATE -> new OrderSpecifier<>(order, universeJpaEntity.createdTime);
            case VIEWS -> new OrderSpecifier<>(order, universeJpaEntity.viewCount);
        };
    }
}
