package com.hoo.admin.application.port.out.house;

import com.hoo.admin.domain.house.room.Room;

public interface CreateRoomPort {
    Room createRoom(String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId);
}
