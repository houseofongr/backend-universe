package com.hoo.admin.application.port.in.item;

import com.hoo.common.application.port.in.MessageDto;

public interface UpdateItemUseCase {
    MessageDto updateItem(Long id, UpdateItemCommand command);
}
