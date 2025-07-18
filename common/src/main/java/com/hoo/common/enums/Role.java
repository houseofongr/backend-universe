package com.hoo.common.enums;

import java.util.List;

public enum Role {
    SUPER_ADMIN, ADMIN, USER, TEMP_USER, VISITOR;

    public static Role parse(String s) {
        if (s.equalsIgnoreCase(SUPER_ADMIN.name())) return SUPER_ADMIN;
        else if (s.equalsIgnoreCase(ADMIN.name())) return ADMIN;
        else if (s.equalsIgnoreCase(USER.name())) return USER;
        else if (s.equalsIgnoreCase(TEMP_USER.name())) return TEMP_USER;
        else if (s.equalsIgnoreCase(VISITOR.name())) return VISITOR;
        else throw new UnsupportedOperationException();
    }

    public List<Authority> getAuthorities() {
        return switch (this) {
            case SUPER_ADMIN -> List.of(Authority.CREATE_ADMIN);
            case ADMIN -> List.of(Authority.ALL_PRIVATE_AUDIO_ACCESS);
            case USER -> List.of(Authority.ALL_PRIVATE_IMAGE_ACCESS, Authority.MY_PRIVATE_AUDIO_ACCESS);
            case TEMP_USER -> List.of(Authority.USER_REGISTER);
            case VISITOR -> List.of(Authority.PUBLIC_FILE_ACCESS);
        };
    }
}
