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

    public void update(User author) {
        this.id = author.getUserInfo().getId();
        this.nickname = author.getUserInfo().getNickname();
    }

    public void update(Long authorId) {
        this.id = authorId;
    }
}
