package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.universe.adapter.out.persistence.entity.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, Long> {
    Optional<CategoryJpaEntity> findByUuid(UUID categoryID);

    void deleteByUuid(UUID categoryID);
}
