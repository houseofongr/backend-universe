package com.hoo.admin.application.port.in.user;

import com.hoo.common.application.port.in.MessageDto;

public interface DeleteUserUseCase {
    MessageDto deleteUser(Long userId, DeleteUserCommand command);
}
