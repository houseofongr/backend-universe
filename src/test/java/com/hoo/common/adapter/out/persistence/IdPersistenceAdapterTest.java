package com.hoo.common.adapter.out.persistence;

import com.hoo.common.adapter.out.persistence.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import(IdPersistenceAdapter.class)
class IdPersistenceAdapterTest {

    @Autowired
    IdPersistenceAdapter sut;

    @Autowired
    HouseJpaRepository houseJpaRepository;

    @Autowired
    RoomJpaRepository roomJpaRepository;

    @Autowired
    HomeJpaRepository homeJpaRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Test
    @Sql("IdPersistenceAdapterTest.sql")
    @DisplayName("ID 생성 테스트")
    void testCreateId() {
        long houseCount = houseJpaRepository.count();
        long roomCount = roomJpaRepository.count();
        long homeCount = homeJpaRepository.count();
        long itemCount = itemJpaRepository.count();
        long universeCount = universeJpaRepository.count();
        long spaceCount = universeJpaRepository.count();

        // when
        Long houseId = sut.issueHouseId();
        Long roomId = sut.issueRoomId();
        Long homeId = sut.issueHomeId();
        Long itemId = sut.issueItemId();
        Long id = sut.issueUniverseId();
        Long spaceId = sut.issueSpaceId();

        // then
        assertThat(houseId).isEqualTo(houseCount + 1);
        assertThat(roomId).isEqualTo(roomCount + 1);
        assertThat(homeId).isEqualTo(homeCount + 1);
        assertThat(itemId).isEqualTo(itemCount + 1);
        assertThat(id).isEqualTo(universeCount + 1);
        assertThat(spaceId).isEqualTo(spaceCount + 1);
    }

}