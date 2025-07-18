package com.hoo.aar.application.port.in.home;

import com.hoo.common.application.port.in.MessageDto;

public interface UpdateHomeNameUseCase {
    MessageDto updateHomeName(Long userId, Long homeId, String homeName);
}
