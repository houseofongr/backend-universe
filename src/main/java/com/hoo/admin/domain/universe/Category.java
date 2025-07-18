package com.hoo.admin.domain.universe;

import java.util.Arrays;

public enum Category {
    GOVERNMENT_AND_PUBLIC_INSTITUTION,
    HEALTH_INSTITUTION,
    LIFE,
    FASHION_AND_BEAUTY;

    public static boolean contains(String s) {
        return Arrays.stream(Category.values()).anyMatch(category -> s.equalsIgnoreCase(category.name()));
    }
}
