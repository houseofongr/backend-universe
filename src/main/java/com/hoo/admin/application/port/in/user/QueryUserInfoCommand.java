package com.hoo.admin.application.port.in.user;

import org.springframework.data.domain.Pageable;

public record QueryUserInfoCommand(
        Pageable pageable
) {
}
