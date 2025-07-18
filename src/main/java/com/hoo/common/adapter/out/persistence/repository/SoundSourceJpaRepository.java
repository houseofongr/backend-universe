package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundSourceJpaRepository extends JpaRepository<SoundSourceJpaEntity, Long>, SoundSourceQueryDslRepository {
}
