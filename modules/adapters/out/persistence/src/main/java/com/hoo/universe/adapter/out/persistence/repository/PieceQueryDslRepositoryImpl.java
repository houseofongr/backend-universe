package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.out.persistence.condition.PieceSortType;
import com.hoo.universe.adapter.out.persistence.entity.SoundJpaEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

import static com.hoo.universe.adapter.out.persistence.entity.QPieceJpaEntity.pieceJpaEntity;
import static com.hoo.universe.adapter.out.persistence.entity.QSoundJpaEntity.soundJpaEntity;

public class PieceQueryDslRepositoryImpl implements PieceQueryDslRepository {

    private final JPAQueryFactory query;

    public PieceQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public PageQueryResult<SoundJpaEntity> searchAllSoundsById(UUID pieceID, PageRequest pageRequest) {
        List<SoundJpaEntity> soundJpaEntities = query.selectFrom(soundJpaEntity)
                .leftJoin(soundJpaEntity.piece, pieceJpaEntity).fetchJoin()
                .where(pieceJpaEntity.uuid.eq(pieceID))
                .orderBy(order(pageRequest))
                .offset(pageRequest.offset())
                .limit(pageRequest.size())
                .fetch();

        Long count = query.select(soundJpaEntity.count())
                .from(soundJpaEntity)
                .leftJoin(soundJpaEntity.piece, pieceJpaEntity)
                .where(pieceJpaEntity.uuid.eq(pieceID))
                .fetchFirst();

        return PageQueryResult.from(pageRequest, count, soundJpaEntities);
    }

    private OrderSpecifier<?> order(PageRequest command) {
        if (command.isAsc() == null || command.sortType() == null || !PieceSortType.contains(command.sortType()))
            return new OrderSpecifier<>(Order.DESC, soundJpaEntity.commonMetadata.createdTime);

        Order order = command.isAsc() ? Order.ASC : Order.DESC;
        return switch (PieceSortType.valueOf(command.sortType().toUpperCase())) {
            case TITLE -> new OrderSpecifier<>(order, soundJpaEntity.commonMetadata.title);
            case REGISTERED_DATE -> new OrderSpecifier<>(order, soundJpaEntity.commonMetadata.createdTime);
        };
    }
}
