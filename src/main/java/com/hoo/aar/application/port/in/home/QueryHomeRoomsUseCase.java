package com.hoo.aar.application.port.in.home;

public interface QueryHomeRoomsUseCase {
    QueryHomeRoomsResult queryHomeRooms(Long userId, Long homeId);
}
