package com.hoo.aar.application.port.in.user;

import com.hoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.common.application.port.in.MessageDto;

public interface DeleteMyInfoUseCase {
    MessageDto deleteMyInfo(Long userId, DeleteUserCommand command);
}
