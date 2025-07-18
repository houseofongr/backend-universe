package com.hoo.admin.application.service.room;

import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.port.out.room.DeleteRoomPort;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.common.application.service.MockEntityFactoryService;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class DeleteRoomServiceTest {

    DeleteRoomService sut;

    FindRoomPort findRoomPort;
    FindItemPort findItemPort;
    DeleteRoomPort deleteRoomPort;
    DeleteFileUseCase deleteFileUseCase;

    @BeforeEach
    void init() {
        findRoomPort = mock();
        findItemPort = mock();
        deleteRoomPort = mock();
        deleteFileUseCase = mock();
        sut = new DeleteRoomService(findRoomPort, findItemPort, deleteRoomPort, deleteFileUseCase);
    }


    @Test
    @DisplayName("룸 삭제 서비스 테스트")
    void testDeleteRoomRoom() throws Exception {
        // given
        Long id = 1L;
        Long existItemRoomId = 2L;

        // when
        when(findRoomPort.load(id)).thenReturn(Optional.of(MockEntityFactoryService.getRoom()));
        when(findRoomPort.load(existItemRoomId)).thenReturn(Optional.of(MockEntityFactoryService.getRoom2()));
        when(findItemPort.existItemByRoomId(existItemRoomId)).thenReturn(true);
        MessageDto messageDto = sut.deleteRoom(id);

        // then
        verify(deleteRoomPort, times(1)).deleteRoom(id);
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        assertThatThrownBy(() -> sut.deleteRoom(1234L)).hasMessage(AdminErrorCode.ROOM_NOT_FOUND.getMessage());
        assertThatThrownBy(() -> sut.deleteRoom(existItemRoomId)).hasMessage(AdminErrorCode.HOLDING_ITEM_ROOM_DELETE.getMessage());
        assertThat(messageDto.message()).isEqualTo("1번 룸이 삭제되었습니다.");
    }

}