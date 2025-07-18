package com.hoo.admin.application.service.room;

import com.hoo.admin.application.port.in.room.QueryRoomItemsResult;
import com.hoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.domain.item.ItemType;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class QueryRoomServiceTest {

    QueryRoomService sut;

    FindRoomPort findRoomPort;
    FindItemPort findItemPort;

    @BeforeEach
    void init() {
        findRoomPort = mock();
        findItemPort = mock();
        sut = new QueryRoomService(findRoomPort, findItemPort);
    }

    @Test
    @DisplayName("룸 조회 서비스 테스트")
    void testQueryRoomService() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findRoomPort.load(1L)).thenReturn(Optional.of(MockEntityFactoryService.getRoom()));
        QueryRoomResult result = sut.queryRoom(id);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("룸 아이템 조회 서비스 테스트")
    void testQueryRoomItemService() throws Exception {
        // given
        Long homeId = 5L;
        Long roomId = 1L;

        // when
        when(findRoomPort.load(roomId)).thenReturn(Optional.of(MockEntityFactoryService.getRoom()));
        when(findItemPort.loadAllItemsInHomeAndRoom(homeId, roomId)).thenReturn(List.of(MockEntityFactoryService.getCircleItem()));
        QueryRoomItemsResult result = sut.queryRoomItems(homeId, roomId);

        // then
        assertThat(result.room().name()).isEqualTo("거실");
        assertThat(result.room().width()).isEqualTo(5000);
        assertThat(result.room().height()).isEqualTo(1000);
        assertThat(result.items()).hasSize(1);
        assertThat(result.items().getFirst().itemType()).isEqualTo(ItemType.CIRCLE);
        assertThat(result.items().getFirst().name()).isEqualTo("강아지");
    }
}