package com.hoo.admin.application.port.out.snsaccount;

import com.hoo.admin.domain.user.snsaccount.SnsAccount;

public interface SaveSnsAccountPort {
    void save(SnsAccount snsAccount);
}
