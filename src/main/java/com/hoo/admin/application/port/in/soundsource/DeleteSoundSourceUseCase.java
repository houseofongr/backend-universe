package com.hoo.admin.application.port.in.soundsource;

import com.hoo.common.application.port.in.MessageDto;

public interface DeleteSoundSourceUseCase {
    MessageDto deleteSoundSource(Long id);
}
