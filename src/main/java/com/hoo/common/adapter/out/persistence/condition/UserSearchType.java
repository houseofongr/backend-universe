package com.hoo.common.adapter.out.persistence.condition;

import java.util.Arrays;

public enum UserSearchType {
    NAME, NICKNAME, EMAIL, PHONE_NUMBER;

    public static boolean contains(String s) {
        return Arrays.stream(UserSearchType.values()).anyMatch(searchType -> s.equalsIgnoreCase(searchType.name()));
    }
}
