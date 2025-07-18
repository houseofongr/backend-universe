package com.hoo.admin.application.port.in.user;

import com.hoo.admin.domain.user.DeletedUser;

public interface SaveDeletedUserPort {
    Long saveDeletedUser(DeletedUser deletedUser);
}
