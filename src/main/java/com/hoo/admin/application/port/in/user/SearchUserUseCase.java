package com.hoo.admin.application.port.in.user;

public interface SearchUserUseCase {
    SearchUserResult query(SearchUserCommand command);
}
