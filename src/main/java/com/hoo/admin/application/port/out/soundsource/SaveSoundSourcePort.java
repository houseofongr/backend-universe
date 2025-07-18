package com.hoo.admin.application.port.out.soundsource;

import com.hoo.admin.domain.item.soundsource.SoundSource;

public interface SaveSoundSourcePort {
    Long saveSoundSource(SoundSource soundSource);
}
