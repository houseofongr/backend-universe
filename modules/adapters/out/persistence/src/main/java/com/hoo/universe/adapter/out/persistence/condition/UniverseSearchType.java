package com.hoo.universe.adapter.out.persistence.condition;

import java.util.Arrays;

public enum UniverseSearchType {
    CONTENT, AUTHOR, ALL;

    public static boolean contains(String s) {
        return Arrays.stream(UniverseSearchType.values()).anyMatch(searchType -> s.equalsIgnoreCase(searchType.name()));
    }
}
