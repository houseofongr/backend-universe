package com.hoo.common.adapter.out.persistence.condition;

import java.util.Arrays;

public enum UniverseSortType {
    TITLE, REGISTERED_DATE, VIEWS;

    public static boolean contains(String s) {
        return Arrays.stream(UniverseSortType.values()).anyMatch(sortType -> s.equalsIgnoreCase(sortType.name()));
    }
}
