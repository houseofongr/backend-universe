package com.hoo.aar.application.port.out.persistence.user;

public record BusinessUserInfo(
        Long businessUserId,
        Long userId,
        String email,
        String password,
        String nickname
) {
}
