package com.hoo.admin.application.service.item;

import com.hoo.admin.application.port.in.item.*;
import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.item.*;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.domain.item.ItemType;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateItemServiceTest {

    CreateItemService sut;

    FindUserPort findUserPort;
    FindHomePort findHomePort;
    FindRoomPort findRoomPort;
    FindItemPort findItemPort;
    UpdateItemPort updateItemPort;
    SaveItemPort saveItemPort;
    CreateItemPort createItemPort;
    MappingItemShapePort mappingItemPort;

    @BeforeEach
    void init() {
        findUserPort = mock();
        findHomePort = mock();
        findItemPort = mock();
        updateItemPort = mock();
        findRoomPort = mock();
        saveItemPort = mock();
        createItemPort = mock();
        mappingItemPort = mock();
        sut = new CreateItemService(findUserPort, findHomePort, findRoomPort, findItemPort, updateItemPort, saveItemPort, createItemPort, mappingItemPort);
    }

    @Test
    @DisplayName("아이템 생성 서비스 테스트")
    void testCreateItem() throws Exception {
        // given
        CreateItemCommand command = new CreateItemCommand(
                List.of(
                        new ItemData(null, "강아지", ItemType.CIRCLE, new ItemData.CircleData(200f, 200f, 10.5f), null, null),
                        new ItemData(null, "설이", ItemType.RECTANGLE, null, new ItemData.RectangleData(100f, 100f, 10f, 10f, 5f), null),
                        new ItemData(null, "화분", ItemType.ELLIPSE, null, null, new ItemData.EllipseData(500f, 500f, 15f, 15f, 90f))
                )
        );

        // when
        when(findUserPort.exist(1L)).thenReturn(true);
        when(findHomePort.exist(1L)).thenReturn(true);
        when(findRoomPort.exist(1L)).thenReturn(true);
        when(saveItemPort.save(any(), any(), any())).thenReturn(List.of(1L, 2L, 3L));
        CreateItemResult createItemResult = sut.create(1L, 1L, 1L, command);

        // then
        verify(saveItemPort, times(1)).save(any(), any(), any());

        assertThat(createItemResult).isNotNull();
        assertThat(createItemResult.createdItemIds()).hasSize(3);
    }

    @Test
    @DisplayName("아이템 생성 및 수정 서비스 테스트")
    void testCreateAndUpdateItem() throws Exception {
        // given
        CreateAndUpdateItemCommand command = new CreateAndUpdateItemCommand(
                List.of(
                        new ItemData(null, "강아지", ItemType.CIRCLE, new ItemData.CircleData(200f, 200f, 10.5f), null, null),
                        new ItemData(null, "설이", ItemType.RECTANGLE, null, new ItemData.RectangleData(100f, 100f, 10f, 10f, 5f), null),
                        new ItemData(null, "화분", ItemType.ELLIPSE, null, null, new ItemData.EllipseData(500f, 500f, 15f, 15f, 90f))
                ),
                List.of(
                        new ItemData(1L, "강아지 수정", ItemType.CIRCLE, new ItemData.CircleData(201f, 220f, 10.5f), null, null),
                        new ItemData(2L, "설이 수정", ItemType.RECTANGLE, null, new ItemData.RectangleData(102f, 130f, 11f, 12f, 4f), null),
                        new ItemData(3L, "화분 수정", ItemType.ELLIPSE, null, null, new ItemData.EllipseData(512f, 502f, 14f, 14f, 9f))
                )
        );

        // when
        when(findUserPort.exist(1L)).thenReturn(true);
        when(findHomePort.exist(1L)).thenReturn(true);
        when(findRoomPort.exist(1L)).thenReturn(true);
        when(saveItemPort.save(any(), any(), any())).thenReturn(new ArrayList<>(Arrays.asList(4L, 5L, 6L)));
        when(findItemPort.loadAllItemsInHomeAndRoom(1L, 1L)).thenReturn(List.of(MockEntityFactoryService.getCircleItem(1L), MockEntityFactoryService.getRectangleItem(2L), MockEntityFactoryService.getEllipseItem(3L)));
        CreateAndUpdateItemResult result = sut.createAndUpdate(1L, 1L, 1L, command);

        // then
        verify(saveItemPort, times(1)).save(any(), any(), any());
        verify(updateItemPort, times(3)).updateItem(any());
        assertThat(result.itemIds()).hasSize(6);
        assertThat(result.itemIds()).contains(1L, 2L, 3L, 4L, 5L, 6L);
    }
}