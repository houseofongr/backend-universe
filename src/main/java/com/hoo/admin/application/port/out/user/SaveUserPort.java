package com.hoo.admin.application.port.out.user;

import com.hoo.admin.domain.user.User;

public interface SaveUserPort {
    Long save(User user);
}
