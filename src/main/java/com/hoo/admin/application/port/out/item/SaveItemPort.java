package com.hoo.admin.application.port.out.item;

import com.hoo.admin.domain.item.Item;

import java.util.List;

public interface SaveItemPort {
    List<Long> save(Long homeId, Long roomId, List<Item> items);
}
