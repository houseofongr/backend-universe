package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.DeletedUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletedUserJpaRepository extends JpaRepository<DeletedUserJpaEntity, Long> {

    Optional<DeletedUserJpaEntity> findByMaskedRealName(String maskedRealName);
}
