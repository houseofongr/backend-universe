package com.hoo.file.domain;

import lombok.Getter;

@Getter
public class OwnerId {
    Long id;

    public OwnerId(Long id) {
        this.id = id;
    }

    public static OwnerId empty() {
        return new OwnerId(null);
    }

    @Override
    public String toString() {
        return "OwnerId{" +
               "universeId=" + id +
               '}';
    }
}
