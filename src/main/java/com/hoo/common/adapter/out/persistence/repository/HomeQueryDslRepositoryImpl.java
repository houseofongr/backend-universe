package com.hoo.common.adapter.out.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class HomeQueryDslRepositoryImpl implements HomeQueryDslRepository {

    private final JPAQueryFactory query;

    public HomeQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

}
