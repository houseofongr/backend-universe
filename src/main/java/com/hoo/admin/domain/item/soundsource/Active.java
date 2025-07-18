package com.hoo.admin.domain.item.soundsource;

import lombok.Getter;

@Getter
public class Active {
    private final boolean isActive;

    public Active(Boolean isActive) {
        if (isActive == null) this.isActive = true;
        else this.isActive = isActive;
    }
}
