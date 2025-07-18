package com.hoo.admin.application.port.in.user;

import com.hoo.admin.domain.user.DeletedUser;
import com.hoo.admin.domain.user.User;

public interface CreateDeletedUserPort {
    DeletedUser createDeletedUser(User user, Boolean termsOfDeletionAgreement, Boolean personalInformationDeletionAgreement);
}
