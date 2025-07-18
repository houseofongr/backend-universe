package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundJpaRepository extends JpaRepository<SoundJpaEntity, Long> {
}
