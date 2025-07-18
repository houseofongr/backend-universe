package com.hoo.common.application.service;

import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.home.Home;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.admin.domain.item.Item;
import com.hoo.admin.domain.item.Rectangle;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.admin.domain.user.User;
import com.hoo.common.application.port.out.IssueIdPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EntityFactoryServiceTest {

    EntityFactoryService sut;
    IssueIdPort issueIdPort;

    @BeforeEach
    void init() {
        issueIdPort = mock();
        sut = new EntityFactoryService(issueIdPort);
    }

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws AreaLimitExceededException {
        // given

        // when
        when(issueIdPort.issueHouseId()).thenReturn(1L);
        House house = sut.createHouse("title", "author", "description", 5000f, 5000f, 1L, 1L, List.of());

        // then
        assertThat(house).isNotNull();
        assertThat(house.getHouseId().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("룸 생성 테스트")
    void testCreateRoom() throws AxisLimitExceededException, AreaLimitExceededException {
        // given

        // when
        when(issueIdPort.issueRoomId()).thenReturn(1L);
        Room room = sut.createRoom("name", 0f, 0f, 0f, 1f, 1f, 1L);

        // then
        assertThat(room).isNotNull();
        assertThat(room.getRoomId().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("홈 생성 테스트")
    void testCreateHome() throws Exception {
        // given
        House house = MockEntityFactoryService.getHouse();
        User user = MockEntityFactoryService.getUser();

        // when
        when(issueIdPort.issueHomeId()).thenReturn(1L);
        Home home = sut.createHome(house, user);

        // then
        assertThat(home).isNotNull();
        assertThat(home.getHomeId().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("아이템 생성 테스트")
    void testCreateItem() throws Exception {
        // given
        List<SoundSource> soundSource = List.of(MockEntityFactoryService.getSoundSource());

        // when
        when(issueIdPort.issueItemId()).thenReturn(1L);
        Item 설이 = sut.createItem(20L, 1L, 10L, "설이", new Rectangle(0f, 0f, 1f, 1f, 0f));

        // then
        assertThat(설이).isNotNull();
        assertThat(설이.getItemId().getId()).isEqualTo(1L);
    }
}