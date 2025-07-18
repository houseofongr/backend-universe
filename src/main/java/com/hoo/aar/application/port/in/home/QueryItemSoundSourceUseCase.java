package com.hoo.aar.application.port.in.home;

public interface QueryItemSoundSourceUseCase {
    QueryItemSoundSourcesResult queryItemSoundSources(Long userId, Long itemId);
}
