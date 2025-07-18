package com.hoo.admin.application.port.in.item;

public interface CreateAndUpdateItemUseCase {
    CreateAndUpdateItemResult createAndUpdate(Long homeId, Long roomId, Long userId, CreateAndUpdateItemCommand command);
}
