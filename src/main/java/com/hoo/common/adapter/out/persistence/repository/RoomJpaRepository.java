package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomJpaRepository extends JpaRepository<RoomJpaEntity, Long>, RoomQueryDslRepository {

    List<RoomJpaEntity> findAllByHouseId(Long houseId);

    void deleteAllByHouseId(Long houseId);

    boolean existsByHouseIdAndId(Long houseId, Long roomId);
}
