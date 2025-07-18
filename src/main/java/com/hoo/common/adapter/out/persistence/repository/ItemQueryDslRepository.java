package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;

import java.util.List;

public interface ItemQueryDslRepository {
    boolean existsByUserIdAndItemId(Long userId, Long itemId);

    List<ItemJpaEntity> findAllByUserId(Long userId);
}
