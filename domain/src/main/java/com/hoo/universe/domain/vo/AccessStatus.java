package com.hoo.universe.domain.vo;

public enum AccessStatus {
    PUBLIC, PRIVATE;

    public static AccessStatus of(String s) {
        for (AccessStatus status : AccessStatus.values()) {
            if (status.name().equalsIgnoreCase(s)) return status;
        }

        throw new IllegalArgumentException();
    }
}
