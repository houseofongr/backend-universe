package com.hoo.aar.application.port.out.persistence.home;

public interface CheckOwnerPort {
    boolean checkHome(Long userId, Long homeId);

    boolean checkRoom(Long homeId, Long roomId);

    boolean checkItem(Long userId, Long itemId);

    boolean checkSoundSource(Long homeId, Long soundSourceId);
}
