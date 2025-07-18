package com.hoo.admin.application.port.in.house;

public record UpdateHouseInfoCommand(
        Long persistenceId,
        String title,
        String owner,
        String description
) {
}
