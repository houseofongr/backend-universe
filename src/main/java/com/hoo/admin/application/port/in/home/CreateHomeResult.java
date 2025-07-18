package com.hoo.admin.application.port.in.home;

public record CreateHomeResult(
        Long createdHomeId,
        String createdHomeName
) {
}
