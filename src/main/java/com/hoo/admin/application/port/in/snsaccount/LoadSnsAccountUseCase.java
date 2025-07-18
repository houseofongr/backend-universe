package com.hoo.admin.application.port.in.snsaccount;

import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;

public interface LoadSnsAccountUseCase {
    SnsAccount loadSnsAccount(SnsDomain domain, String snsId);
}
