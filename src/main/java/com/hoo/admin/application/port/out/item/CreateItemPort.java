package com.hoo.admin.application.port.out.item;

import com.hoo.admin.domain.item.Item;
import com.hoo.admin.domain.item.Shape;

public interface CreateItemPort {
    Item createItem(Long homeId, Long roomId, Long userId, String name, Shape shape);
}
