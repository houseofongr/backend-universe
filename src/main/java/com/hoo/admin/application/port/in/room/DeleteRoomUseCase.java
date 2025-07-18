package com.hoo.admin.application.port.in.room;

import com.hoo.common.application.port.in.MessageDto;

public interface DeleteRoomUseCase {
    MessageDto deleteRoom(Long roomId);
}
