package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.HashtagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagJpaRepository extends JpaRepository<HashtagJpaEntity, Long> {
    Optional<HashtagJpaEntity> findByTag(String tag);
}
