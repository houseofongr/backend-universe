package com.hoo.admin.application.port.out.item;

import com.hoo.admin.application.port.in.item.ItemData;
import com.hoo.admin.domain.item.Shape;

public interface MappingItemShapePort {
    Shape mapToShape(ItemData itemData);
}
