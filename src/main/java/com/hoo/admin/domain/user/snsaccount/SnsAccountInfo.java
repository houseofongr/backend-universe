package com.hoo.admin.domain.user.snsaccount;

import lombok.Getter;

@Getter
public class SnsAccountInfo {
    private final String realName;
    private final String nickname;
    private final String email;

    public SnsAccountInfo(String realName, String nickname, String email) {
        this.realName = realName;
        this.nickname = nickname;
        this.email = email;
    }
}
