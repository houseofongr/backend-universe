package com.hoo.admin.application.port.in.item;

public interface CreateItemUseCase {
    CreateItemResult create(Long homeId, Long roomId, Long userId, CreateItemCommand command);
}
