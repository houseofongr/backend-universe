package com.hoo.admin.application.service.room;

import com.hoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.application.port.out.room.UpdateRoomPort;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateRoomServiceTest {

    UpdateRoomService sut;

    FindRoomPort findRoomPort;
    UpdateRoomPort updateRoomPort;

    @BeforeEach
    void init() {
        findRoomPort = mock();
        updateRoomPort = mock();
        sut = new UpdateRoomService(findRoomPort, updateRoomPort);
    }

    @Test
    @DisplayName("룸 업데이트 서비스 테스트")
    void testUpdateRoomInfo() throws Exception {
        // given
        UpdateRoomInfoCommand command = new UpdateRoomInfoCommand(List.of(new UpdateRoomInfoCommand.RoomInfo(1L, "욕실")));
        UpdateRoomInfoCommand notFoundCommand = new UpdateRoomInfoCommand(List.of(new UpdateRoomInfoCommand.RoomInfo(100L, "욕실")));
        List<Room> rooms = List.of(Room.load(1L, "거실", 1000f, 1000f, 0f, 5000f, 1000f, 1L));

        // when
        when(updateRoomPort.update(rooms)).thenReturn(1);
        when(findRoomPort.loadAll(List.of(1L))).thenReturn(rooms);
        MessageDto message = sut.update(command);

        // then
        verify(updateRoomPort, times(1)).update(any());

        assertThat(message.message()).isEqualTo("1개 룸의 정보 수정이 완료되었습니다.");
        assertThat(sut.update(notFoundCommand).message()).isEqualTo("0개 룸의 정보 수정이 완료되었습니다.");
    }

}