package com.hoo.admin.domain.universe;

import com.hoo.admin.domain.user.User;
import lombok.Getter;

@Getter
public class AuthorInfo {
    private Long id;
    private String nickname;

    public AuthorInfo(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public void update(User owner) {
        this.id = owner.getUserInfo().getId();
        this.nickname = owner.getUserInfo().getNickname();
    }

    public void update(Long ownerId) {
        this.id = ownerId;
    }
}
