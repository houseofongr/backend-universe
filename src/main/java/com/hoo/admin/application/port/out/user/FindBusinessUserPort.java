package com.hoo.admin.application.port.out.user;

import com.hoo.admin.domain.user.BusinessUser;

public interface FindBusinessUserPort {
    BusinessUser findBusinessUser(Long businessUserId);
}
