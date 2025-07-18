package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.universe.adapter.out.persistence.entity.SoundJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface SoundJpaRepository extends JpaRepository<SoundJpaEntity, Long> {
    void deleteAllByUuidIn(Collection<UUID> uuids);

    Optional<SoundJpaEntity> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
