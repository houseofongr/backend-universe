package com.hoo.admin.application.service.house;

import com.hoo.admin.application.port.in.room.DeleteRoomUseCase;
import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.house.DeleteHousePort;
import com.hoo.admin.application.port.out.house.FindHousePort;
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

class DeleteHouseServiceTest {

    DeleteHouseService sut;

    FindHousePort findHousePort;
    FindHomePort findHomePort;
    DeleteHousePort deleteHousePort;
    DeleteRoomUseCase deleteRoomUseCase;
    DeleteFileUseCase deleteFileUseCase;

    @BeforeEach
    void init() {
        findHousePort = mock();
        findHomePort = mock();
        deleteHousePort = mock();
        deleteRoomUseCase = mock();
        deleteFileUseCase = mock();
        sut = new DeleteHouseService(findHousePort, findHomePort, deleteHousePort, deleteRoomUseCase, deleteFileUseCase);
    }

    @Test
    @DisplayName("하우스 삭제 서비스 테스트")
    void testDeleteHouseHouse() throws Exception {
        // given
        Long id = 1L;
        Long existHomeHouseId = 2L;

        // when
        when(findHousePort.load(id)).thenReturn(Optional.of(MockEntityFactoryService.getHouse()));
        when(findHousePort.load(existHomeHouseId)).thenReturn(Optional.of(MockEntityFactoryService.getHouse()));
        when(findHomePort.existByHouseId(existHomeHouseId)).thenReturn(true);
        MessageDto messageDto = sut.deleteHouse(id);

        // then
        verify(deleteHousePort, times(1)).deleteHouse(anyLong());
        verify(deleteFileUseCase, times(2)).deleteFile(anyLong());
        verify(deleteRoomUseCase, times(2)).deleteRoom(anyLong());
        assertThatThrownBy(() -> sut.deleteHouse(1234L)).hasMessage(AdminErrorCode.HOUSE_NOT_FOUND.getMessage());
        assertThatThrownBy(() -> sut.deleteHouse(existHomeHouseId)).hasMessage(AdminErrorCode.HOLDING_HOME_HOUSE_DELETE.getMessage());
        assertThat(messageDto.message()).isEqualTo("1번 하우스가 삭제되었습니다.");
    }

}