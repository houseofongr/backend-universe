package com.hoo.admin.application.port.out.sound;

import com.hoo.admin.application.port.in.sound.CreateSoundCommand;
import com.hoo.admin.domain.universe.piece.sound.Sound;

public interface CreateSoundPort {
    Sound createSound(Long audioId, CreateSoundCommand command);
}
