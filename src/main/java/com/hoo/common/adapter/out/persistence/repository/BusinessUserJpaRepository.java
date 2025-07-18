package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.BusinessUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessUserJpaRepository extends JpaRepository<BusinessUserJpaEntity, Long> {
    Optional<BusinessUserJpaEntity> findByEmail(String email);
}
