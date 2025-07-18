package com.hoo.admin.application.port.out.user;

import com.hoo.admin.domain.user.User;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;

public interface CreateUserPort {
    User createUser(SnsAccount snsAccount, Boolean termsOfUseAgreement, Boolean personalInformationAgreement);
}
