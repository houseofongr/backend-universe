package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.admin.application.port.in.piece.SearchPieceCommand;
import com.hoo.common.adapter.out.persistence.condition.PieceSortType;
import com.hoo.common.adapter.out.persistence.condition.UniverseSortType;
import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.hoo.common.adapter.out.persistence.entity.QPieceJpaEntity.pieceJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QSoundJpaEntity.soundJpaEntity;

public class PieceQueryDslRepositoryImpl implements PieceQueryDslRepository {

    private final JPAQueryFactory query;

    public PieceQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<SoundJpaEntity> searchAll(SearchPieceCommand command) {

        List<SoundJpaEntity> soundJpaEntities = query.selectFrom(soundJpaEntity)
                .leftJoin(soundJpaEntity.piece, pieceJpaEntity).fetchJoin()
                .where(pieceJpaEntity.id.eq(command.pieceId()))
                .orderBy(order(command))
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(soundJpaEntity.count())
                .from(soundJpaEntity)
                .leftJoin(soundJpaEntity.piece, pieceJpaEntity)
                .where(pieceJpaEntity.id.eq(command.pieceId()))
                .fetchFirst();

        return new PageImpl<>(soundJpaEntities, command.pageable(), count == null ? 0 : count);
    }

    private OrderSpecifier<?> order(SearchPieceCommand command) {
        if (command.isAsc() == null || command.sortType() == null || !UniverseSortType.contains(command.sortType()))
            return new OrderSpecifier<>(Order.DESC, soundJpaEntity.createdTime);

        Order order = command.isAsc() ? Order.ASC : Order.DESC;
        return switch (PieceSortType.valueOf(command.sortType().toUpperCase())) {
            case TITLE -> new OrderSpecifier<>(order, soundJpaEntity.title);
            case REGISTERED_DATE -> new OrderSpecifier<>(order, soundJpaEntity.createdTime);
        };
    }
}
