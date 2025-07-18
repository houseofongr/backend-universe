package com.hoo.aar.application.port.in.home;

public interface QueryRoomItemsUseCase {
    QueryRoomItemsResult queryRoomItems(Long userId, Long homeId, Long roomId);
}
