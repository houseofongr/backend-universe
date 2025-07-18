package com.hoo.aar.application.port.in.home;

import com.hoo.common.application.port.in.MessageDto;

public interface UpdateMainHomeUseCase {
    MessageDto updateMainHome(Long userId, Long homeId);
}
