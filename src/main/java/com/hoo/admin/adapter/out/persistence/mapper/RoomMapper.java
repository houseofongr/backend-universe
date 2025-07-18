package com.hoo.admin.adapter.out.persistence.mapper;

import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    public Room mapToDomainEntity(RoomJpaEntity roomJpaEntity) throws AreaLimitExceededException, AxisLimitExceededException {
        return Room.load(
                roomJpaEntity.getId(),
                roomJpaEntity.getName(),
                roomJpaEntity.getX(),
                roomJpaEntity.getY(),
                roomJpaEntity.getZ(),
                roomJpaEntity.getWidth(),
                roomJpaEntity.getHeight(),
                roomJpaEntity.getImageFileId());
    }
}
