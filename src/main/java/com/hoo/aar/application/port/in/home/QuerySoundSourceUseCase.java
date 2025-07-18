package com.hoo.aar.application.port.in.home;

public interface QuerySoundSourceUseCase {
    QuerySoundSourceResult querySoundSource(Long userId, Long soundSourceId);
}
