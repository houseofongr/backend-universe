package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.QItemJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.hoo.common.adapter.out.persistence.entity.QHomeJpaEntity.homeJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QItemJpaEntity.itemJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QUserJpaEntity.userJpaEntity;

public class ItemQueryDslRepositoryImpl implements ItemQueryDslRepository {

    private final JPAQueryFactory query;

    public ItemQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public boolean existsByUserIdAndItemId(Long userId, Long itemId) {
        return query.selectOne()
                       .from(itemJpaEntity)
                       .leftJoin(itemJpaEntity.home, homeJpaEntity)
                       .leftJoin(homeJpaEntity.user, userJpaEntity)
                       .where(itemJpaEntity.id.eq(itemId)
                               .and(userJpaEntity.id.eq(userId)))
                       .fetchFirst() != null;
    }

    @Override
    public List<ItemJpaEntity> findAllByUserId(Long userId) {
        return query.selectFrom(QItemJpaEntity.itemJpaEntity)
                .leftJoin(itemJpaEntity.home, homeJpaEntity)
                .leftJoin(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(userId))
                .fetch();
    }
}
