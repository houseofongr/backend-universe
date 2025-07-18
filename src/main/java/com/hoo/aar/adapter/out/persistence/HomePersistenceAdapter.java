package com.hoo.aar.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aar.application.port.in.home.*;
import com.hoo.aar.application.port.out.persistence.home.CheckOwnerPort;
import com.hoo.aar.application.port.out.persistence.home.QueryHomePort;
import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import com.hoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AARHomePersistenceAdapter")
@RequiredArgsConstructor
public class HomePersistenceAdapter implements QueryHomePort, CheckOwnerPort {

    private final HomeJpaRepository homeJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final SoundSourceJpaRepository soundSourceJpaRepository;
    private final HomeMapper homeMapper;

    @Override
    public QueryUserHomesResult queryUserHomes(Long userId) {
        return homeMapper.mapToQueryUserHomes(homeJpaRepository.findAllByUserId(userId));
    }

    @Override
    public QueryHomeRoomsResult queryHomeRooms(Long homeId) {
        return homeMapper.mapToQueryHomeRooms(homeJpaRepository.findById(homeId).orElseThrow());
    }

    @Override
    public QueryRoomItemsResult queryRoomItems(Long homeId, Long roomId) {
        RoomJpaEntity roomJpaEntity = roomJpaRepository.findById(roomId).orElseThrow();
        List<ItemJpaEntity> itemJpaEntities = itemJpaRepository.findAllByHomeIdAndRoomId(homeId, roomId);
        return homeMapper.mapToQueryRoomItems(roomJpaEntity, itemJpaEntities);
    }

    @Override
    public QueryItemSoundSourcesResult queryItemSoundSources(Long itemId) {
        return homeMapper.mapToQueryItemSoundSources(itemJpaRepository.findById(itemId).orElseThrow());
    }

    @Override
    public QuerySoundSourceResult querySoundSource(Long soundSourceId) {
        return homeMapper.mapToQuerySoundSource(soundSourceJpaRepository.findById(soundSourceId).orElseThrow());
    }

    @Override
    public QuerySoundSourcesPathResult querySoundSourcesPath(QuerySoundSourcesPathCommand command) {
        Page<QuerySoundSourcesPathResult.SoundSourcePathInfo> result = soundSourceJpaRepository.findAllActivatedByIdWithPathEntity(command).map(homeMapper::mapToSoundSourcePathInfo);
        return new QuerySoundSourcesPathResult(result.getContent(), Pagination.of(result));
    }

    @Override
    public boolean checkHome(Long userId, Long homeId) {
        return homeJpaRepository.existsByUserIdAndId(userId, homeId);
    }

    @Override
    public boolean checkRoom(Long homeId, Long roomId) {
        Long houseId = homeJpaRepository.findById(homeId).orElseThrow().getHouse().getId();
        return roomJpaRepository.existsByHouseIdAndId(houseId, roomId);
    }

    @Override
    public boolean checkItem(Long userId, Long itemId) {
        return itemJpaRepository.existsByUserIdAndItemId(userId, itemId);
    }

    @Override
    public boolean checkSoundSource(Long userId, Long soundSourceId) {
        return soundSourceJpaRepository.existsByUserIdAndId(userId, soundSourceId);
    }
}
