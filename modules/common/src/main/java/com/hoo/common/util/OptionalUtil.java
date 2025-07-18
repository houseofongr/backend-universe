package com.hoo.common.util;

public class OptionalUtil {

    public static boolean orElseFalse(Boolean value) {
        return value != null && value;
    }

    public static  <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
