package com.hoo.common.adapter.out.persistence.condition;

import java.util.Arrays;

public enum UserSortType {
    NAME, NICKNAME, REGISTERED_DATE;

    public static boolean contains(String s) {
        return Arrays.stream(UserSortType.values()).anyMatch(sortType -> s.equalsIgnoreCase(sortType.name()));
    }
}
