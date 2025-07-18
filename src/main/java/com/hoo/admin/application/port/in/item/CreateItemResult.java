package com.hoo.admin.application.port.in.item;

import java.util.List;

public record CreateItemResult(
        List<Long> createdItemIds
) {
}
