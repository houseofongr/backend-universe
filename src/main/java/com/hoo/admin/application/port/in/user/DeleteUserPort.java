package com.hoo.admin.application.port.in.user;

import com.hoo.admin.domain.user.User;

public interface DeleteUserPort {
    void deleteUser(User user);
}
