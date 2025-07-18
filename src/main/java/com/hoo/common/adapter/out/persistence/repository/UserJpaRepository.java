package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long>, UserQueryDslRepository {
    Optional<UserJpaEntity> findByPhoneNumber(String phoneNumber);
    boolean existsByNickname(String nickname);
}
