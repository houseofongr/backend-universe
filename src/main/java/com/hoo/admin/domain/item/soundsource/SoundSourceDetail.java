package com.hoo.admin.domain.item.soundsource;

import lombok.Getter;

@Getter
public class SoundSourceDetail {

    public SoundSourceDetail(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private final String name;
    private final String description;
}
