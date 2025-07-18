package com.hoo.admin.application.port.out.sound;

import com.hoo.admin.domain.universe.piece.sound.Sound;

public interface FindSoundPort {
    Sound find(Long id);
}
