package com.hoo.admin.domain.user;

import lombok.Getter;

@Getter
public class UserId {

    private final Long id;

    public UserId(Long id) {
        this.id = id;
    }
}
