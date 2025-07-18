package com.hoo.common.adapter.out.persistence.condition;

import java.util.Arrays;

public enum PieceSortType {
    TITLE, REGISTERED_DATE;

    public static boolean contains(String s) {
        return Arrays.stream(PieceSortType.values()).anyMatch(sortType -> s.equalsIgnoreCase(sortType.name()));
    }
}
