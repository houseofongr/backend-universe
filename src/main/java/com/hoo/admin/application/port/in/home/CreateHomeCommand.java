package com.hoo.admin.application.port.in.home;

import jakarta.validation.constraints.NotNull;

public record CreateHomeCommand(
        @NotNull Long userId,
        @NotNull Long houseId
) {
}
