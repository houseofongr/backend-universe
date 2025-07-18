package com.hoo.admin.application.port.in.house;

import com.hoo.common.application.port.in.MessageDto;

public interface UpdateHouseInfoUseCase {
    MessageDto update(UpdateHouseInfoCommand command);
}
