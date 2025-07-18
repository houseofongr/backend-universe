package com.hoo.admin.application.port.in.house;

import com.hoo.common.application.port.in.MessageDto;

public interface DeleteHouseUseCase {
    MessageDto deleteHouse(Long id);
}
