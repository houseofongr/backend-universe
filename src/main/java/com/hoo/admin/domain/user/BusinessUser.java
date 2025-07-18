package com.hoo.admin.domain.user;

import com.hoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class BusinessUser {

    private final Long businessUserId;
    private final UserInfo userInfo;
    private final Agreement agreement;
    private final BaseTime baseTime;

    public BusinessUser(Long businessUserId, UserInfo userInfo, Agreement agreement, BaseTime baseTime) {
        this.businessUserId = businessUserId;
        this.userInfo = userInfo;
        this.agreement = agreement;
        this.baseTime = baseTime;
    }

    public static BusinessUser create(Long id, String nickname, String email, Boolean termsOfUseAgreement, Boolean personalInformationAgreement) {
        return new BusinessUser(id,
                new UserInfo(null, null, nickname, email),
                new Agreement(termsOfUseAgreement, personalInformationAgreement),
                null);
    }

    public static BusinessUser load(Long id, String nickname, String email, Boolean termsOfUseAgreement, Boolean personalInformationAgreement, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new BusinessUser(id,
                new UserInfo(null, null, nickname, email),
                new Agreement(termsOfUseAgreement, personalInformationAgreement),
                new BaseTime(createdTime, updatedTime));
    }
}
