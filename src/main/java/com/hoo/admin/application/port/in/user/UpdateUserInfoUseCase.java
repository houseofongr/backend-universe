package com.hoo.admin.application.port.in.user;

import com.hoo.common.application.port.in.MessageDto;

public interface UpdateUserInfoUseCase {
    MessageDto updateUserInfo(Long userId, UpdateUserInfoCommand command);
}
