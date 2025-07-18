package com.hoo.admin.application.port.in.soundsource;

import org.springframework.data.domain.Pageable;

public record QuerySoundSourceListCommand(
        Pageable pageable
) {
}
