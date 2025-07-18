package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.out.persistence.condition.UniverseSearchType;
import com.hoo.universe.adapter.out.persistence.condition.UniverseSortType;
import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

import static com.hoo.universe.adapter.out.persistence.entity.QUniverseJpaEntity.universeJpaEntity;
import static com.hoo.universe.adapter.out.persistence.entity.QUniverseTagJpaEntity.universeTagJpaEntity;

public class UniverseQueryDslRepositoryImpl implements UniverseQueryDslRepository {

    private final JPAQueryFactory query;

    public UniverseQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public PageQueryResult<UniverseJpaEntity> searchUniverse(UUID categoryID, PageRequest pageRequest) {
        List<UniverseJpaEntity> entities = query.selectFrom(universeJpaEntity)
                .leftJoin(universeJpaEntity.universeHashtags, universeTagJpaEntity).fetchJoin()
                .where(search(pageRequest.keyword(), pageRequest.searchType()))
                .where(filterByCategory(categoryID))
                .orderBy(order(pageRequest.sortType(), pageRequest.isAsc()))
                .offset(pageRequest.offset())
                .limit(pageRequest.size())
                .fetch();

        Long count = query.select(universeJpaEntity.count())
                .from(universeJpaEntity)
                .where(search(pageRequest.keyword(), pageRequest.searchType()))
                .where(filterByCategory(categoryID))
                .fetchFirst();

        return PageQueryResult.from(pageRequest, count, entities);
    }

    private BooleanExpression filterByCategory(UUID categoryID) {
        return categoryID != null ? universeJpaEntity.category.uuid.eq(categoryID) : null;
    }

    private BooleanExpression search(String keyword, String searchType) {
        if (keyword == null || keyword.isBlank() || !UniverseSearchType.contains(searchType))
            return null;

        return switch (UniverseSearchType.valueOf(searchType.toUpperCase())) {

            case CONTENT -> universeJpaEntity.commonMetadata.title.likeIgnoreCase("%" + keyword + "%")
                    .or(universeJpaEntity.commonMetadata.description.likeIgnoreCase("%" + keyword + "%"));

            case AUTHOR -> universeJpaEntity.owner.ownerNickname.likeIgnoreCase("%" + keyword + "%");

            case ALL -> universeJpaEntity.commonMetadata.title.likeIgnoreCase("%" + keyword + "%")
                    .or(universeJpaEntity.commonMetadata.description.likeIgnoreCase("%" + keyword + "%"))
                    .or(universeJpaEntity.owner.ownerNickname.likeIgnoreCase("%" + keyword + "%"));
        };
    }

    private OrderSpecifier<?> order(String sortType, Boolean isAsc) {
        if (isAsc == null || sortType == null || !UniverseSortType.contains(sortType))
            return new OrderSpecifier<>(Order.DESC, universeJpaEntity.commonMetadata.createdTime);

        Order order = isAsc ? Order.ASC : Order.DESC;
        return switch (UniverseSortType.valueOf(sortType.toUpperCase())) {

            case TITLE -> new OrderSpecifier<>(order, universeJpaEntity.commonMetadata.title);

            case REGISTERED_DATE -> new OrderSpecifier<>(order, universeJpaEntity.commonMetadata.createdTime);

            case VIEWS -> new OrderSpecifier<>(order, universeJpaEntity.universeMetadata.viewCount);
        };
    }
}
