package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.hoo.common.adapter.out.persistence.entity.QHouseJpaEntity.houseJpaEntity;

public class HouseQueryDslRepositoryImpl implements HouseQueryDslRepository {

    private final JPAQueryFactory query;

    public HouseQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<HouseJpaEntity> searchByCommand(QueryHouseListCommand command) {

        List<HouseJpaEntity> entities = query.selectFrom(houseJpaEntity)
                .where(houseSearch(command))
                .orderBy(houseJpaEntity.createdTime.desc())
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(houseJpaEntity.count())
                .from(houseJpaEntity)
                .where(houseSearch(command))
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count == null ? 0 : count);
    }

    private BooleanExpression houseSearch(QueryHouseListCommand command) {
        if (command.keyword() == null || command.keyword().isBlank()) return null;
        return switch (command.searchType()) {
            case TITLE -> houseJpaEntity.title.likeIgnoreCase("%" + command.keyword() + "%");
            case AUTHOR -> houseJpaEntity.author.likeIgnoreCase("%" + command.keyword() + "%");
            case DESCRIPTION -> houseJpaEntity.description.likeIgnoreCase("%" + command.keyword() + "%");
        };
    }
}
