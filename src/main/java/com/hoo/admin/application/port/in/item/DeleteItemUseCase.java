package com.hoo.admin.application.port.in.item;

import com.hoo.common.application.port.in.MessageDto;

public interface DeleteItemUseCase {
    MessageDto deleteItem(Long id);
}
