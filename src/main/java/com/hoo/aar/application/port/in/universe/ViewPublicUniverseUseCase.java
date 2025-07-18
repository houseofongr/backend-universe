package com.hoo.aar.application.port.in.universe;

public interface ViewPublicUniverseUseCase {
    ViewPublicUniverseResult read(Long universeId, Long userId);
}
