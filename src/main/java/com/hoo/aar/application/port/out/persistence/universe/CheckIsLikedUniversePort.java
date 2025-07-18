package com.hoo.aar.application.port.out.persistence.universe;

public interface CheckIsLikedUniversePort {
    boolean checkIsLiked(Long universeId, Long userId);
}
