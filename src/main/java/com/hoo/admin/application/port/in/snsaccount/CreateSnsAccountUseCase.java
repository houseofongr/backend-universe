package com.hoo.admin.application.port.in.snsaccount;

import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;

public interface CreateSnsAccountUseCase {
    SnsAccount createSnsAccount(SnsDomain domain, String snsId, String realName, String nickname, String email);
}
