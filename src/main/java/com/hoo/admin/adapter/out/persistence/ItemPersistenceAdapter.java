package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.admin.application.port.in.item.ItemData;
import com.hoo.admin.application.port.out.item.*;
import com.hoo.admin.domain.item.Item;
import com.hoo.admin.domain.item.Shape;
import com.hoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemPersistenceAdapter implements SaveItemPort, FindItemPort, UpdateItemPort, DeleteItemPort, MappingItemShapePort {

    private final HomeJpaRepository homeJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<Long> save(Long homeId, Long roomId, List<Item> items) {

        HomeJpaEntity homeJpaEntity = homeJpaRepository.findById(homeId).orElseThrow();
        RoomJpaEntity roomJpaEntity = roomJpaRepository.findById(roomId).orElseThrow();

        List<ItemJpaEntity> itemJpaEntities = items.stream().map(ItemJpaEntity::create).toList();
        itemJpaEntities.forEach(itemJpaEntity -> itemJpaEntity.setRelationship(homeJpaEntity, roomJpaEntity));

        itemJpaRepository.saveAll(itemJpaEntities);

        return itemJpaEntities.stream().map(ItemJpaEntity::getId).toList();
    }

    @Override
    public Optional<Item> loadItem(Long id) {
        return itemJpaRepository.findById(id)
                .map(itemMapper::mapToDomainEntity);
    }

    @Override
    public List<Item> loadAllItemsInHome(Long homeId) {
        return itemJpaRepository.findAllByHomeId(homeId)
                .stream().map(itemMapper::mapToDomainEntity).toList();
    }

    @Override
    public List<Item> loadAllItemsInHomeAndRoom(Long homeId, Long roomId) {
        return itemJpaRepository.findAllByHomeIdAndRoomId(homeId, roomId)
                .stream().map(itemMapper::mapToDomainEntity).toList();
    }

    @Override
    public boolean existItemByRoomId(Long roomId) {
        return itemJpaRepository.existsByRoomId(roomId);
    }

    @Override
    public void updateItem(Item item) {
        ItemJpaEntity itemJpaEntity = itemJpaRepository.findById(item.getItemId().getId()).orElseThrow();
        itemJpaEntity.update(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemJpaRepository.deleteById(id);
    }

    @Override
    public Shape mapToShape(ItemData itemData) {
        return itemMapper.mapToShape(itemData);
    }
}
