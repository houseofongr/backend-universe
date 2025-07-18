package com.hoo.admin.application.port.out.snsaccount;

import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;

import java.util.Optional;

public interface FindSnsAccountPort {
    Optional<SnsAccount> loadSnsAccount(SnsDomain domain, String snsId);

    Optional<SnsAccount> loadSnsAccount(Long id);
}
