package com.hoo.admin.application.service.item;

import com.hoo.admin.application.port.out.item.DeleteItemPort;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.item.Item;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteItemServiceTest {

    DeleteItemService sut;
    FindItemPort findItemPort;
    DeleteItemPort deleteItemPort;

    @BeforeEach
    void init() {
        findItemPort = mock();
        deleteItemPort = mock();
        sut = new DeleteItemService(findItemPort, deleteItemPort);
    }

    @Test
    @DisplayName("아이템 삭제 서비스 테스트")
    void testDeleteItemService() throws Exception {
        // given
        Long id = 1L;
        Long notFoundId = 1234L;
        Long hasSoundSourceId = 2L;
        Item rectangleItem = MockEntityFactoryService.getRectangleItem();
        Item rectangleItemWithSoundSource = MockEntityFactoryService.loadRectangleItem();

        // when
        when(findItemPort.loadItem(id)).thenReturn(Optional.ofNullable(rectangleItem));
        when(findItemPort.loadItem(hasSoundSourceId)).thenReturn(Optional.ofNullable(rectangleItemWithSoundSource));
        MessageDto messageDto = sut.deleteItem(id);

        // then
        verify(deleteItemPort, times(1)).deleteItem(id);
        assertThatThrownBy(() -> sut.deleteItem(notFoundId)).hasMessage(AdminErrorCode.ITEM_NOT_FOUND.getMessage());
        assertThatThrownBy(() -> sut.deleteItem(hasSoundSourceId)).hasMessage(AdminErrorCode.ITEM_HAS_SOUND_SOURCE.getMessage());
        assertThat(messageDto.message()).isEqualTo(id + "번 아이템이 삭제되었습니다.");
    }
}