package com.hoo.admin.domain.item.soundsource;

import lombok.Getter;

@Getter
public class SoundSourceId {
    private final Long id;

    public SoundSourceId(Long id) {
        this.id = id;
    }
}
