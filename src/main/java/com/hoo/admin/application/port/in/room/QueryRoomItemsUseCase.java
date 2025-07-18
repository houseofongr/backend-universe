package com.hoo.admin.application.port.in.room;

public interface QueryRoomItemsUseCase {
    QueryRoomItemsResult queryRoomItems(Long homeId, Long roomId);
}
