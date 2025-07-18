package com.hoo.admin.application.service.home;

import com.hoo.admin.application.port.out.home.DeleteHomePort;
import com.hoo.admin.application.port.out.item.DeleteItemPort;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteHomeServiceTest {

    DeleteHomeService sut;

    FindItemPort findItemPort;
    DeleteItemPort deleteItemPort;
    DeleteHomePort deleteHomePort;

    @BeforeEach
    void init() {
        findItemPort = mock();
        deleteItemPort = mock();
        deleteHomePort = mock();
        sut = new DeleteHomeService(findItemPort, deleteItemPort, deleteHomePort);
    }

    @Test
    @DisplayName("홈 삭제 테스트")
    void testDeleteHomeHome() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findItemPort.loadAllItemsInHome(id)).thenReturn(List.of(MockEntityFactoryService.getCircleItem(), MockEntityFactoryService.getEllipseItem(), MockEntityFactoryService.getRectangleItem()));
        MessageDto messageDto = sut.deleteHome(id);

        // then
        verify(deleteItemPort, times(3)).deleteItem(anyLong());
        verify(deleteHomePort, times(1)).deleteHome(id);
        assertThat(messageDto.message()).isEqualTo(id + "번 홈이 삭제되었습니다.");
    }
}