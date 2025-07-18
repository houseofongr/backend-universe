package com.hoo.admin.application.port.in.soundsource;

public record CreateSoundSourceMetadata(
        String name,
        String description,
        Boolean isActive
) {
}
