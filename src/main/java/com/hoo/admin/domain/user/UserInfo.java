package com.hoo.admin.domain.user;

import lombok.Getter;

@Getter
public class UserInfo {
    private final Long id;
    private final String realName;
    private String nickname;
    private final String email;

    public UserInfo(Long id, String realName, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.realName = realName;
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMaskedRealName() {
        return getMaskedName(realName);
    }

    public String getMaskedNickname() {
        return getMaskedName(nickname);
    }

    private String getMaskedName(String name) {
        String[] splitName = name.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < splitName.length; i++) {
            stringBuilder.append(maskingWord(splitName[i]));
            if (i != splitName.length - 1) stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private String maskingWord(String name) {
        if (name.length() == 1)
            return "*";

        else if (name.length() == 2)
            return name.charAt(0) + "*";

        else
            return name.charAt(0) +
                   "*".repeat(name.length() - 2) +
                   name.charAt(name.length() - 1);
    }

    public String getMaskedEmail() {
        String[] splitName = email.split("@");
        return maskingWord(splitName[0]) + "@" + splitName[1];
    }
}
