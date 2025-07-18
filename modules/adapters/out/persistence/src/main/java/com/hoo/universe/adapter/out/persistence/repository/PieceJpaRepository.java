package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.universe.adapter.out.persistence.entity.PieceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PieceJpaRepository extends JpaRepository<PieceJpaEntity, Long>, PieceQueryDslRepository {
    void deleteAllByUuidIn(List<UUID> uuids);

    @Query("select p.uuid from PieceJpaEntity p where p.id = :id")
    UUID findUuidById(Long id);

    Optional<PieceJpaEntity> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
