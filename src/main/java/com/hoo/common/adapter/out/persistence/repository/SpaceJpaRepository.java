package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceJpaRepository extends JpaRepository<SpaceJpaEntity, Long> {
}
