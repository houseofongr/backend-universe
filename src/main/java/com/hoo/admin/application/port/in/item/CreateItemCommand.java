package com.hoo.admin.application.port.in.item;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateItemCommand(
        @NotEmpty List<ItemData> items
) {
}
