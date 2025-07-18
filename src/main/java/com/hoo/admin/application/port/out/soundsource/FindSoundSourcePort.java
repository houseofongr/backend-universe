package com.hoo.admin.application.port.out.soundsource;

import com.hoo.admin.domain.item.soundsource.SoundSource;

import java.util.Optional;

public interface FindSoundSourcePort {
    Optional<SoundSource> loadSoundSource(Long id);
}
