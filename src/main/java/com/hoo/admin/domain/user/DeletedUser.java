package com.hoo.admin.domain.user;

import com.hoo.common.domain.BaseTime;
import lombok.Getter;

@Getter
public class DeletedUser {

    private final DeletedUserId userId;
    private final UserInfo userInfo;
    private final DeleteAgreement agreement;
    private final BaseTime baseTime;

    public DeletedUser(DeletedUserId userId, UserInfo userInfo, DeleteAgreement agreement, BaseTime baseTime) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.agreement = agreement;
        this.baseTime = baseTime;
    }

    public static DeletedUser create(Long deletedUserId, User user, Boolean termsOfDeletionAgreement, Boolean personalInformationDeletionAgreement) {

        return new DeletedUser(
                new DeletedUserId(deletedUserId),
                user.getDeletedUserInfo(),
                new DeleteAgreement(termsOfDeletionAgreement, personalInformationDeletionAgreement),
                null);
    }
}
