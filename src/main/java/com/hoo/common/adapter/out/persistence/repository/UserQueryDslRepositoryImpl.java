package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.common.adapter.out.persistence.condition.UserSearchType;
import com.hoo.common.adapter.out.persistence.condition.UserSortType;
import com.hoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.hoo.common.adapter.out.persistence.entity.QHomeJpaEntity.homeJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QItemJpaEntity.itemJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QSnsAccountJpaEntity.snsAccountJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QSoundSourceJpaEntity.soundSourceJpaEntity;
import static com.hoo.common.adapter.out.persistence.entity.QUserJpaEntity.userJpaEntity;

public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {

    private final JPAQueryFactory query;

    public UserQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<UserJpaEntity> searchAll(QueryUserInfoCommand command) {
        List<UserJpaEntity> entities = query.selectFrom(userJpaEntity)
                .leftJoin(userJpaEntity.snsAccountEntities, snsAccountJpaEntity).fetchJoin()
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(userJpaEntity.count())
                .from(userJpaEntity)
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count);
    }

    @Override
    public Page<UserJpaEntity> searchAll(SearchUserCommand command) {
        List<UserJpaEntity> entities = query.selectFrom(userJpaEntity)
                .leftJoin(userJpaEntity.snsAccountEntities, snsAccountJpaEntity).fetchJoin()
                .where(search(command))
                .orderBy(order(command))
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(userJpaEntity.count())
                .from(userJpaEntity)
                .where(search(command))
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count);
    }

    private BooleanExpression search(SearchUserCommand command) {
        if (command.keyword() == null || command.keyword().isBlank() || !UserSearchType.contains(command.searchType()))
            return null;

        return switch (UserSearchType.valueOf(command.searchType().toUpperCase())) {
            case NAME -> userJpaEntity.realName.likeIgnoreCase("%" + command.keyword() + "%");
            case NICKNAME -> userJpaEntity.nickname.likeIgnoreCase("%" + command.keyword() + "%");
            case EMAIL -> userJpaEntity.email.likeIgnoreCase("%" + command.keyword() + "%");
            case PHONE_NUMBER -> userJpaEntity.phoneNumber.like("%" + command.keyword() + "%");
        };
    }

    private OrderSpecifier<?> order(SearchUserCommand command) {
        if (command.isAsc() == null || command.sortType() == null || !UserSortType.contains(command.sortType()))
            return new OrderSpecifier<>(Order.DESC, userJpaEntity.createdTime);

        Order order = command.isAsc() ? Order.ASC : Order.DESC;
        return switch (UserSortType.valueOf(command.sortType().toUpperCase())) {
            case NAME -> new OrderSpecifier<>(order, userJpaEntity.realName);
            case NICKNAME -> new OrderSpecifier<>(order, userJpaEntity.nickname);
            case REGISTERED_DATE -> new OrderSpecifier<>(order, userJpaEntity.createdTime);
        };
    }

    @Override
    public Integer countHomeCountById(Long userId) {
        Long count = query.select(homeJpaEntity.count())
                .from(homeJpaEntity)
                .join(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(userId))
                .fetchFirst();

        return count == null ? 0 : Math.toIntExact(count);
    }

    @Override
    public Integer countActiveSoundSourceCountById(Long userId) {
        Long count = query.select(soundSourceJpaEntity.count())
                .from(soundSourceJpaEntity)
                .join(soundSourceJpaEntity.item, itemJpaEntity)
                .join(itemJpaEntity.home, homeJpaEntity)
                .join(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(userId)
                        .and(soundSourceJpaEntity.isActive.isTrue()))
                .fetchFirst();

        return count == null ? 0 : Math.toIntExact(count);
    }

}
