package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.admin.adapter.out.persistence.mapper.RoomMapper;
import com.hoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({RoomPersistenceAdapter.class, RoomMapper.class, ItemMapper.class, SoundSourceMapper.class})
class RoomPersistenceAdapterTest {

    @Autowired
    RoomPersistenceAdapter sut;

    @Autowired
    RoomJpaRepository roomJpaRepository;

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 수정 테스트")
    void testUpdateRoomInfo() throws Exception {
        // given
        List<Room> rooms = List.of(Room.create(1L, "욕실", 1f, 1f, 1f, 5000f, 1000f, 1L),
                Room.create(2L, "주방", 1f, 1f, 1f, 5000f, 1000f, 1L));

        // when
        int update = sut.update(rooms);
        Optional<RoomJpaEntity> query = roomJpaRepository.findById(1L);

        // then
        assertThat(query).isNotEmpty();
        assertThat(query.get().getName()).isEqualTo("욕실");
        assertThat(update).isEqualTo(1);
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 조회 테스트")
    void testFindResult() throws AreaLimitExceededException, AxisLimitExceededException {
        // given
        Long id = 2L;

        // when
        Optional<Room> result = sut.load(id);
        Optional<Room> result2 = sut.load(1234L);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getRoomDetail().getName()).isEqualTo("주방");
        assertThat(result.get().getArea().getWidth()).isEqualTo(5000);
        assertThat(result.get().getArea().getHeight()).isEqualTo(1000);
        assertThat(result.get().getImageFile().getFileId().getId()).isEqualTo(6);

        // not found
        assertThat(result2.isEmpty());
    }


    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 삭제 테스트")
    void testDeleteHouseRoomRoom() {
        // given
        Long roomId = 1L;

        // when
        sut.deleteRoom(roomId);

        // then
        assertThat(roomJpaRepository.findById(roomId)).isEmpty();
    }
}