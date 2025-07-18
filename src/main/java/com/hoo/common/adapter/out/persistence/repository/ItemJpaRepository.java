package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, Long>, ItemQueryDslRepository {
    boolean existsByRoomId(Long roomId);

    List<ItemJpaEntity> findAllByHomeId(Long homeId);

    List<ItemJpaEntity> findAllByHomeIdAndRoomId(Long homeId, Long roomId);
}
