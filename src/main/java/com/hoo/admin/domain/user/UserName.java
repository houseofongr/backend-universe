package com.hoo.admin.domain.user;

import lombok.Getter;

@Getter
public class UserName {
    private final String nickName;
    private final String realName;

    public UserName(String nickName, String realName) {
        this.realName = realName;
        this.nickName = nickName;
    }
}
