package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.admin.adapter.out.persistence.mapper.RoomMapper;
import com.hoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.house.House;
import com.hoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.common.adapter.out.persistence.condition.HouseSearchType;
import com.hoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({HousePersistenceAdapter.class, HouseMapper.class, RoomMapper.class, ItemMapper.class, SoundSourceMapper.class})
class HousePersistenceAdapterTest {

    @Autowired
    HousePersistenceAdapter sut;

    @Autowired
    HouseJpaRepository houseJpaRepository;

    @Autowired
    EntityManager em;

    @Autowired
    RoomJpaRepository roomJpaRepository;

    @Test
    @DisplayName("House 저장 테스트")
    void testSaveHouse() throws Exception {
        // given
        House newHouse = MockEntityFactoryService.loadHouse();

        // when
        Long savedId = sut.save(newHouse);
        em.flush();
        em.clear();

        Optional<HouseJpaEntity> entity = houseJpaRepository.findById(savedId);

        // then
        assertThat(savedId).isNotNull();
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().getTitle()).isEqualTo("cozy house");
        assertThat(entity.get().getAuthor()).isEqualTo("leaf");
        assertThat(entity.get().getDescription()).isEqualTo("this is cozy house");
        assertThat(entity.get().getRooms()).hasSize(2);
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("HouseJpaEntity 페이지 조회 테스트")
    void testSearchJpaEntityHouse() {
        // given
        Pageable pageable = PageRequest.of(0, 9);

        QueryHouseListCommand allCommand = new QueryHouseListCommand(pageable, null, null);
        QueryHouseListCommand keywordCommand = new QueryHouseListCommand(pageable, HouseSearchType.TITLE, "cozy");
        QueryHouseListCommand keywordCommand2 = new QueryHouseListCommand(pageable, HouseSearchType.AUTHOR, "leAf");
        QueryHouseListCommand keywordCommand3 = new QueryHouseListCommand(pageable, HouseSearchType.DESCRIPTION, "IS");
        QueryHouseListCommand nonKeywordCommand = new QueryHouseListCommand(pageable, HouseSearchType.DESCRIPTION, "no keyword");

        // when
        QueryHouseListResult result = sut.search(allCommand);
        QueryHouseListResult searchResult = sut.search(keywordCommand);
        QueryHouseListResult searchResult2 = sut.search(keywordCommand2);
        QueryHouseListResult searchResult3 = sut.search(keywordCommand3);
        QueryHouseListResult noResult = sut.search(nonKeywordCommand);

        // then
        assertThat(result.houses()).hasSize(9);
        assertThat(result.houses()).anySatisfy(entity -> {
            assertThat(entity.id()).isEqualTo(1L);
            assertThat(entity.title()).isEqualTo("cozy house");
            assertThat(entity.author()).isEqualTo("leaf");
            assertThat(entity.description()).isEqualTo("this is cozy house");
            assertThat(entity.createdDate()).isEqualTo(DateTimeFormatters.ENGLISH_DATE.getFormatter().format(ZonedDateTime.now()));
        });

        // keyword search
        assertThat(searchResult.houses()).hasSize(2);
        assertThat(searchResult2.houses()).hasSize(1);
        assertThat(searchResult3.houses()).hasSize(2);
        assertThat(noResult.houses()).hasSize(0);
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 조회 테스트")
    void testLoad() throws AreaLimitExceededException, AxisLimitExceededException {
        // given
        Long houseId = 1L;

        // when
        Optional<House> house = sut.load(houseId);

        // then
        assertThat(house).isNotEmpty();
        assertThat(house.get().getHouseDetail().getTitle()).isEqualTo("cozy house");
        assertThat(house.get().getHouseDetail().getAuthor()).isEqualTo("leaf");
        assertThat(house.get().getHouseDetail().getDescription()).isEqualTo("this is cozy house");
        assertThat(house.get().getArea().getWidth()).isEqualTo(5000f);
        assertThat(house.get().getArea().getHeight()).isEqualTo(5000f);
        assertThat(house.get().getRooms()).hasSize(2)
                .anySatisfy(room -> {
                    assertThat(room.getRoomDetail().getName()).isEqualTo("거실");
                    assertThat(room.getAxis().getX()).isEqualTo(0);
                    assertThat(room.getAxis().getY()).isEqualTo(0);
                    assertThat(room.getAxis().getZ()).isEqualTo(0);
                    assertThat(room.getArea().getWidth()).isEqualTo(5000);
                    assertThat(room.getArea().getHeight()).isEqualTo(1000);
                });
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 수정 테스트")
    void testUpdateInfoHouse() throws Exception {
        // given
        House houseWithRoom = House.create(2L, "cozy house", "leaf", "this is cozy house", 5000f, 5000f, 3L, 4L, List.of(MockEntityFactoryService.getRoom()));

        // when
        sut.update(houseWithRoom);
        Optional<HouseJpaEntity> query = houseJpaRepository.findById(2L);

        // then
        assertThat(query).isNotEmpty();
        assertThat(query.get().getTitle()).isEqualTo("cozy house");
        assertThat(query.get().getAuthor()).isEqualTo("leaf");
        assertThat(query.get().getDescription()).isEqualTo("this is cozy house");
    }


    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 삭제 테스트")
    void testDeleteHouseRoomHouse() {
        // given
        Long id = 1L;

        // when
        sut.deleteHouse(id);

        // then
        assertThat(houseJpaRepository.findById(id)).isEmpty();
        assertThat(roomJpaRepository.findAllByHouseId(id)).isEmpty();
    }

}