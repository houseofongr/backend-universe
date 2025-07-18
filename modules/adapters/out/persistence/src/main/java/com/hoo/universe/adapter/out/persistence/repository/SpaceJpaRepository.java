package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.universe.adapter.out.persistence.entity.SpaceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpaceJpaRepository extends JpaRepository<SpaceJpaEntity, Long> {
    Optional<SpaceJpaEntity> findByUuid(UUID uuid);

    @Query("select s.uuid from SpaceJpaEntity s where s.id = :id")
    UUID findUuidById(Long id);

    void deleteAllByUuidIn(List<UUID> uuids);
}
