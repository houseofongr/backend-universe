package com.hoo.admin.application.service.item;

import com.hoo.admin.application.port.in.item.ItemData;
import com.hoo.admin.application.port.in.item.UpdateItemCommand;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.port.out.item.MappingItemShapePort;
import com.hoo.admin.application.port.out.item.UpdateItemPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.item.Item;
import com.hoo.admin.domain.item.ItemType;
import com.hoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateItemServiceTest {

    UpdateItemService sut;

    FindItemPort findItemPort;
    UpdateItemPort updateItemPort;
    MappingItemShapePort mappingItemShapePort;

    @BeforeEach
    void init() {
        findItemPort = mock();
        updateItemPort = mock();
        mappingItemShapePort = mock();
        sut = new UpdateItemService(findItemPort, updateItemPort, mappingItemShapePort);
    }

    @Test
    @DisplayName("아이템 업데이트 서비스 테스트")
    void testUpdateItem() {
        // given
        Long id = 1L;
        Long notExistId = 1234L;
        UpdateItemCommand command = new UpdateItemCommand(new ItemData(null, "고양이", ItemType.RECTANGLE, null,
                new ItemData.RectangleData(300f, 300f, 20f, 20f, 10f), null));

        Item item = mock();

        // when
        when(findItemPort.loadItem(id)).thenReturn(Optional.ofNullable(item));
        MessageDto messageDto = sut.updateItem(id, command);

        // then
        verify(item, times(1)).update(any(), any());
        verify(updateItemPort, times(1)).updateItem(any());
        assertThatThrownBy(() -> sut.updateItem(notExistId, command)).hasMessage(AdminErrorCode.ITEM_NOT_FOUND.getMessage());
        assertThat(messageDto.message()).isEqualTo("1번 아이템의 정보가 수정되었습니다.");
    }

}