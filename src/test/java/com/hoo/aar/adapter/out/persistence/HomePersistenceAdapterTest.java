package com.hoo.aar.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aar.application.port.in.home.*;
import com.hoo.admin.domain.item.ItemType;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({HomePersistenceAdapter.class, HomeMapper.class})
@Sql("HomePersistenceAdapterTest.sql")
class HomePersistenceAdapterTest {

    @Autowired
    HomePersistenceAdapter sut;

    @Test
    @DisplayName("사용자 홈 조회 테스트")
    void testQueryUserHomes() {
        // given
        Long userId = 10L;

        // when
        QueryUserHomesResult result = sut.queryUserHomes(userId);

        // then
        assertThat(result.homes()).hasSize(2)
                .anySatisfy(homeInfo -> {
                    assertThat(homeInfo.id()).isEqualTo(1L);
                    assertThat(homeInfo.name()).isEqualTo("leaf의 cozy house");
                })
                .anySatisfy(homeInfo -> {
                    assertThat(homeInfo.id()).isEqualTo(2L);
                    assertThat(homeInfo.name()).isEqualTo("leaf의 simple house");
                });
    }

    @Test
    @DisplayName("사용자 홈 소유여부 확인 테스트")
    void testCheckUserOwnHome() {
        // given
        Long ownedUserId = 10L;
        Long notOwnedUserId = 1234L;
        Long homeId = 1L;
        Long notOwnedHomeId = 5678L;

        // when
        assertThat(sut.checkHome(ownedUserId, homeId)).isTrue();
        assertThat(sut.checkHome(notOwnedUserId, homeId)).isFalse();
        assertThat(sut.checkHome(ownedUserId, notOwnedHomeId)).isFalse();
        assertThat(sut.checkHome(notOwnedUserId, notOwnedHomeId)).isFalse();
    }

    @Test
    @DisplayName("홈 룸 소유여부 확인 테스트")
    void testCheckHomeOwnRoom() {
        // given
        Long ownedHomeId = 1L;
        Long roomId = 2L;
        Long notOwnedRoomId = 5678L;

        // then
        assertThat(sut.checkRoom(ownedHomeId, roomId)).isTrue();
        assertThat(sut.checkRoom(ownedHomeId, notOwnedRoomId)).isFalse();
    }

    @Test
    @DisplayName("홈 아이템 소유여부 확인 테스트")
    void testCheckHomeOwnItem() {
        // given
        Long ownerId = 10L;
        Long itemId = 2L;
        Long notOwnedItemId = 5678L;

        // then
        assertThat(sut.checkItem(ownerId, itemId)).isTrue();
        assertThat(sut.checkItem(ownerId, notOwnedItemId)).isFalse();
    }

    @Test
    @DisplayName("홈 음원 소유여부 확인 테스트")
    void testCheckHomeOwnSoundSource() {
        // given
        Long ownedId = 10L;
        Long soundSourceId = 2L;
        Long notOwnedSoundSourceId = 5678L;

        // then
        assertThat(sut.checkSoundSource(ownedId, soundSourceId)).isTrue();
        assertThat(sut.checkSoundSource(ownedId, notOwnedSoundSourceId)).isFalse();
    }

    @Test
    @DisplayName("홈 룸 조회 테스트")
    void testQueryHomeRooms() {
        // given
        Long homeId = 1L;

        // when
        QueryHomeRoomsResult result = sut.queryHomeRooms(homeId);

        // then
        assertThat(result.homeName()).isEqualTo("leaf의 cozy house");
        assertThat(result.house().borderImageId()).isEqualTo(2L);
        assertThat(result.house().height()).isEqualTo(5000);
        assertThat(result.house().width()).isEqualTo(5000);
        assertThat(result.rooms()).hasSize(2)
                .anySatisfy(roomInfo -> {
                    assertThat(roomInfo.roomId()).isEqualTo(1L);
                    assertThat(roomInfo.name()).isEqualTo("거실");
                    assertThat(roomInfo.width()).isEqualTo(5000);
                    assertThat(roomInfo.height()).isEqualTo(1000);
                    assertThat(roomInfo.x()).isEqualTo(0);
                    assertThat(roomInfo.y()).isEqualTo(0);
                    assertThat(roomInfo.z()).isEqualTo(0);
                    assertThat(roomInfo.imageId()).isEqualTo(5);
                })
                .anySatisfy(roomInfo -> {
                    assertThat(roomInfo.roomId()).isEqualTo(2L);
                    assertThat(roomInfo.name()).isEqualTo("주방");
                    assertThat(roomInfo.width()).isEqualTo(5000);
                    assertThat(roomInfo.height()).isEqualTo(1000);
                    assertThat(roomInfo.x()).isEqualTo(0);
                    assertThat(roomInfo.y()).isEqualTo(1000);
                    assertThat(roomInfo.z()).isEqualTo(0);
                    assertThat(roomInfo.imageId()).isEqualTo(6);
                });
    }

    @Test
    @DisplayName("룸 아이템 조회 테스트")
    void testQueryRoomItems() {
        // given
        Long homeId = 1L;
        Long roomId = 1L;
        Long noItemHomeId = 2L;
        Long noItemRoomId = 2L;

        // when
        QueryRoomItemsResult queryRoomItemsResult = sut.queryRoomItems(homeId, roomId);
        QueryRoomItemsResult noItemResult = sut.queryRoomItems(noItemHomeId, noItemRoomId);

        // then
        assertThat(noItemResult.room().name()).isEqualTo("주방");
        assertThat(noItemResult.items()).isEmpty();

        assertThat(queryRoomItemsResult.room().name()).isEqualTo("거실");
        assertThat(queryRoomItemsResult.room().width()).isEqualTo(5000);
        assertThat(queryRoomItemsResult.room().height()).isEqualTo(1000);
        assertThat(queryRoomItemsResult.room().imageId()).isEqualTo(5);

        assertThat(queryRoomItemsResult.items()).hasSize(2)
                .anySatisfy(itemData -> {
                    assertThat(itemData.id()).isEqualTo(1);
                    assertThat(itemData.name()).isEqualTo("설이");
                    assertThat(itemData.itemType()).isEqualTo(ItemType.RECTANGLE);
                    assertThat(itemData.rectangleData().x()).isEqualTo(100);
                    assertThat(itemData.rectangleData().y()).isEqualTo(100);
                    assertThat(itemData.rectangleData().width()).isEqualTo(10);
                    assertThat(itemData.rectangleData().height()).isEqualTo(10);
                    assertThat(itemData.rectangleData().rotation()).isEqualTo(5);
                    assertThat(itemData.circleData()).isNull();
                    assertThat(itemData.ellipseData()).isNull();
                })
                .anySatisfy(itemData -> {
                    assertThat(itemData.id()).isEqualTo(2);
                    assertThat(itemData.name()).isEqualTo("강아지");
                    assertThat(itemData.itemType()).isEqualTo(ItemType.CIRCLE);
                    assertThat(itemData.circleData().x()).isEqualTo(200);
                    assertThat(itemData.circleData().y()).isEqualTo(200);
                    assertThat(itemData.circleData().radius()).isEqualTo(10.5f);
                    assertThat(itemData.rectangleData()).isNull();
                    assertThat(itemData.ellipseData()).isNull();
                })
                .noneMatch(itemData -> itemData.id().equals(3L));
    }

    @Test
    @DisplayName("아이템 음원 조회 테스트")
    void testQueryItemSoundSources() {
        // given
        Long itemId = 1L;

        // when
        QueryItemSoundSourcesResult result = sut.queryItemSoundSources(itemId);

        // then
        assertThat(result.itemName()).isEqualTo("설이");
        assertThat(result.soundSources()).hasSize(1)
                .anySatisfy(soundSourceInfo -> {
                    assertThat(soundSourceInfo.id()).isEqualTo(1L);
                    assertThat(soundSourceInfo.name()).isEqualTo("골골송");
                    assertThat(soundSourceInfo.description()).isEqualTo("2025년 골골송 V1");
                    assertThat(soundSourceInfo.createdDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
                    assertThat(soundSourceInfo.updatedDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
                });
    }

    @Test
    @DisplayName("음원 조회 테스트")
    void testQuerySoundSources() {
        // given
        Long soundSourceId = 1L;

        // when
        QuerySoundSourceResult result = sut.querySoundSource(soundSourceId);

        // then
        assertThat(result.audioFileId()).isEqualTo(1);
        assertThat(result.name()).isEqualTo("골골송");
        assertThat(result.description()).isEqualTo("2025년 골골송 V1");
        assertThat(result.createdDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
        assertThat(result.updatedDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
    }

    @Test
    @DisplayName("전체음원 경로 조회 테스트")
    void testQuerySoundSourcesPath() {
        // given
        QuerySoundSourcesPathCommand command = new QuerySoundSourcesPathCommand(10L, PageRequest.of(0, 3));
        QuerySoundSourcesPathCommand fourSourceCommand = new QuerySoundSourcesPathCommand(10L, PageRequest.of(0, 4));

        // when
        QuerySoundSourcesPathResult result = sut.querySoundSourcesPath(command);
        QuerySoundSourcesPathResult fourSourceResult = sut.querySoundSourcesPath(fourSourceCommand);

        // then
        assertThat(fourSourceResult.soundSources()).hasSize(4);
        assertThat(result.soundSources()).hasSize(3)
                .anySatisfy(soundSourceInfo -> {
                    assertThat(soundSourceInfo.name()).isEqualTo("골골송");
                    assertThat(soundSourceInfo.description()).isEqualTo("2025년 골골송 V1");
                    assertThat(soundSourceInfo.createdDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
                    assertThat(soundSourceInfo.updatedDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
                    assertThat(soundSourceInfo.audioFileId()).isEqualTo(1);
                    assertThat(soundSourceInfo.homeName()).isEqualTo("leaf의 cozy house");
                    assertThat(soundSourceInfo.homeId()).isEqualTo(1);
                    assertThat(soundSourceInfo.roomName()).isEqualTo("거실");
                    assertThat(soundSourceInfo.roomId()).isEqualTo(1);
                    assertThat(soundSourceInfo.itemName()).isEqualTo("설이");
                    assertThat(soundSourceInfo.itemId()).isEqualTo(1);
                }).anySatisfy(soundSourceInfo -> {
                    assertThat(soundSourceInfo.name()).isEqualTo("멍멍송");
                    assertThat(soundSourceInfo.description()).isEqualTo("2025년 멍멍송 V1");
                    assertThat(soundSourceInfo.createdDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
                    assertThat(soundSourceInfo.updatedDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
                    assertThat(soundSourceInfo.audioFileId()).isEqualTo(2);
                    assertThat(soundSourceInfo.homeId()).isEqualTo(1);
                    assertThat(soundSourceInfo.homeName()).isEqualTo("leaf의 cozy house");
                    assertThat(soundSourceInfo.roomName()).isEqualTo("거실");
                    assertThat(soundSourceInfo.roomId()).isEqualTo(1);
                    assertThat(soundSourceInfo.itemName()).isEqualTo("강아지");
                    assertThat(soundSourceInfo.itemId()).isEqualTo(2);
                });
    }
}