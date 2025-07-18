package com.hoo.admin.application.port.out.room;

import com.hoo.admin.domain.house.room.Room;

import java.util.List;

public interface UpdateRoomPort {
    int update(List<Room> rooms);
}
