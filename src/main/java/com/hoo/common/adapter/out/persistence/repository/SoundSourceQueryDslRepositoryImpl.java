package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.aar.application.port.in.home.QuerySoundSourcesPathCommand;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.hoo.common.adapter.out.persistence.entity.QHomeJpaEntity.homeJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QItemJpaEntity.itemJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QRoomJpaEntity.roomJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QSoundSourceJpaEntity.soundSourceJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QUserJpaEntity.userJpaEntity;

public class SoundSourceQueryDslRepositoryImpl implements SoundSourceQueryDslRepository {

    private final JPAQueryFactory query;

    public SoundSourceQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public boolean existsByUserIdAndId(Long userId, Long soundSourceId) {
        return query.selectOne()
                       .from(soundSourceJpaEntity)
                       .leftJoin(soundSourceJpaEntity.item, itemJpaEntity)
                       .leftJoin(itemJpaEntity.home, homeJpaEntity)
                       .leftJoin(homeJpaEntity.user, userJpaEntity)
                       .where(soundSourceJpaEntity.id.eq(soundSourceId)
                               .and(userJpaEntity.id.eq(userId)))
                       .fetchFirst() != null;
    }

    @Override
    public Page<SoundSourceJpaEntity> findAllWithRelatedEntity(QuerySoundSourceListCommand command) {
        List<SoundSourceJpaEntity> entities = query.selectFrom(soundSourceJpaEntity)
                .leftJoin(soundSourceJpaEntity.item, itemJpaEntity).fetchJoin()
                .leftJoin(itemJpaEntity.home, homeJpaEntity).fetchJoin()
                .leftJoin(itemJpaEntity.room, roomJpaEntity).fetchJoin()
                .leftJoin(homeJpaEntity.user, userJpaEntity).fetchJoin()
                .orderBy(soundSourceJpaEntity.createdTime.desc())
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(soundSourceJpaEntity.count())
                .from(soundSourceJpaEntity)
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count == null ? 0 : count);
    }

    @Override
    public Page<SoundSourceJpaEntity> findAllActivatedByIdWithPathEntity(QuerySoundSourcesPathCommand command) {
        List<SoundSourceJpaEntity> entities = query.selectFrom(soundSourceJpaEntity)
                .leftJoin(soundSourceJpaEntity.item, itemJpaEntity).fetchJoin()
                .leftJoin(itemJpaEntity.home, homeJpaEntity).fetchJoin()
                .leftJoin(itemJpaEntity.room, roomJpaEntity).fetchJoin()
                .leftJoin(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(command.userId())
                        .and(soundSourceJpaEntity.isActive.isTrue()))
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(soundSourceJpaEntity.count())
                .from(soundSourceJpaEntity)
                .leftJoin(soundSourceJpaEntity.item, itemJpaEntity)
                .leftJoin(itemJpaEntity.home, homeJpaEntity)
                .leftJoin(itemJpaEntity.room, roomJpaEntity)
                .leftJoin(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(command.userId())
                        .and(soundSourceJpaEntity.isActive.isTrue()))
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count == null ? 0 : count);
    }

    @Override
    public List<SoundSourceJpaEntity> findAllByUserId(Long userId) {
        return query.selectFrom(soundSourceJpaEntity)
                .leftJoin(soundSourceJpaEntity.item, itemJpaEntity)
                .leftJoin(itemJpaEntity.home, homeJpaEntity)
                .leftJoin(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(userId))
                .fetch();
    }
}
