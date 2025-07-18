package com.hoo.admin.application.port.in.user;

public interface ConfirmBusinessUserUseCase {
    ConfirmBusinessUserResult confirm(Long tempUserId);
}
