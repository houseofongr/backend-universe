package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.admin.application.port.out.house.DeleteHousePort;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.port.out.house.SaveHousePort;
import com.hoo.admin.application.port.out.house.UpdateHousePort;
import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.house.House;
import com.hoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HousePersistenceAdapter implements SaveHousePort, UpdateHousePort, FindHousePort, DeleteHousePort {

    private final HouseJpaRepository houseJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final HouseMapper houseMapper;

    @Override
    public Long save(House house) {

        List<RoomJpaEntity> roomJpaEntities = house.getRooms().stream().map(RoomJpaEntity::create).toList();
        HouseJpaEntity houseJpaEntity = HouseJpaEntity.create(house);

        roomJpaEntities.forEach(roomJpaEntity -> roomJpaEntity.setRelationship(houseJpaEntity));

        houseJpaRepository.save(houseJpaEntity);
        roomJpaRepository.saveAll(roomJpaEntities);

        return houseJpaEntity.getId();
    }

    @SneakyThrows({AreaLimitExceededException.class, AxisLimitExceededException.class})
    @Override
    public Optional<House> load(Long id) {

        Optional<HouseJpaEntity> optional = houseJpaRepository.findById(id);

        if (optional.isEmpty()) return Optional.empty();

        return Optional.of(houseMapper.mapToDomainEntity(optional.get()));
    }

    @Override
    public QueryHouseListResult search(QueryHouseListCommand command) {
        Page<QueryHouseListResult.HouseInfo> houseInfoPages = houseJpaRepository.searchByCommand(command).map(QueryHouseListResult.HouseInfo::of);
        return new QueryHouseListResult(houseInfoPages.getContent(), Pagination.of(houseInfoPages));
    }

    @Override
    public void update(House house) {
        HouseJpaEntity entity = houseJpaRepository.findById(house.getHouseId().getId()).orElseThrow();
        entity.update(house);
    }

    @Override
    public void deleteHouse(Long id) {
        roomJpaRepository.deleteAllByHouseId(id);
        houseJpaRepository.deleteById(id);
    }

}
