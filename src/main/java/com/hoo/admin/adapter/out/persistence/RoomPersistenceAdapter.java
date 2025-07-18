package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.RoomMapper;
import com.hoo.admin.application.port.out.room.DeleteRoomPort;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.application.port.out.room.UpdateRoomPort;
import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoomPersistenceAdapter implements UpdateRoomPort, FindRoomPort, DeleteRoomPort {

    private final RoomJpaRepository roomJpaRepository;
    private final RoomMapper roomMapper;

    @Override
    public boolean exist(Long id) {
        return roomJpaRepository.existsById(id);
    }

    @Override
    @SneakyThrows({AreaLimitExceededException.class, AxisLimitExceededException.class})
    public Optional<Room> load(Long id) {

        Optional<RoomJpaEntity> optional = roomJpaRepository.findById(id);

        if (optional.isEmpty()) return Optional.empty();
        else return Optional.of(roomMapper.mapToDomainEntity(optional.get()));
    }

    @Override
    @SneakyThrows({AreaLimitExceededException.class, AxisLimitExceededException.class})
    public List<Room> loadAll(List<Long> ids) {

        List<Room> list = new ArrayList<>();

        for (RoomJpaEntity roomJpaEntity : roomJpaRepository.findAllById(ids))
            list.add(roomMapper.mapToDomainEntity(roomJpaEntity));

        return list;
    }

    @Override
    public int update(List<Room> rooms) {

        List<Long> roomIds = rooms.stream().map(room -> room.getRoomId().getId()).toList();
        List<RoomJpaEntity> roomJpaEntities = roomJpaRepository.findAllById(roomIds);

        int updateCount = 0;

        loop:
        for (RoomJpaEntity roomJpaEntity : roomJpaEntities) {
            for (Room room : rooms) {
                if (!room.getRoomId().getId().equals(roomJpaEntity.getId())) continue;
                roomJpaEntity.update(room.getRoomDetail().getName());
                updateCount++;
                break loop;
            }
        }

        return updateCount;
    }

    @Override
    public void deleteRoom(Long id) {
        roomJpaRepository.deleteById(id);
    }
}
