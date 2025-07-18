package com.hoo.admin.application.port.in.user;

public interface RegisterUserUseCase {
    RegisterUserResult register(RegisterUserCommand command);
}
