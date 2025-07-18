package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UniverseJpaRepository extends JpaRepository<UniverseJpaEntity, Long>, UniverseQueryDslRepository {
    Optional<UniverseJpaEntity> findByUuid(UUID uuid);

    @Query("select u.uuid from UniverseJpaEntity u where u.id = :id")
    UUID findUuidById(Long id);

    void deleteByUuid(UUID uuid);
}
