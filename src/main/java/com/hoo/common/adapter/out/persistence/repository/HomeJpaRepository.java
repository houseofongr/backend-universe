package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomeJpaRepository extends JpaRepository<HomeJpaEntity, Long>, HomeQueryDslRepository {
    List<HomeJpaEntity> findAllByUserId(Long id);

    boolean existsByHouseIdAndUserId(Long houseId, Long userId);

    boolean existsByHouseId(Long houseId);

    boolean existsByUserIdAndId(Long userId, Long id);

    Optional<HomeJpaEntity> findByUserIdAndIsMain(Long userId, Boolean isMain);
}
