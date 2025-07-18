package com.hoo.admin.domain.user;

import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class User {

    private final UserInfo userInfo;
    private final Agreement agreement;
    private final BaseTime baseTime;
    private final List<SnsAccount> snsAccounts;

    private User(UserInfo userInfo, Agreement agreement, BaseTime baseTime, List<SnsAccount> snsAccounts) {
        this.userInfo = userInfo;
        this.agreement = agreement;
        this.baseTime = baseTime;
        this.snsAccounts = snsAccounts;
    }

    public static User register(Long id, Boolean termsOfUseAgreement, Boolean personalInformationAgreement, SnsAccount snsAccount) {
        return new User(
                new UserInfo(id, snsAccount.getSnsAccountInfo().getRealName(), snsAccount.getSnsAccountInfo().getNickname(), snsAccount.getSnsAccountInfo().getEmail()),
                new Agreement(termsOfUseAgreement, personalInformationAgreement),
                null,
                List.of(snsAccount)
        );
    }

    public static User createBusinessUser(BusinessUser businessUser) {
        return new User(
                businessUser.getUserInfo(),
                businessUser.getAgreement(),
                businessUser.getBaseTime(),
                List.of()
        );
    }

    public static User load(Long id, String realName, String nickname, String email, Boolean termsOfUseAgreement, Boolean personalInformationAgreement, ZonedDateTime createdTime, ZonedDateTime updatedTime, List<SnsAccount> snsAccounts) {
        return new User(
                new UserInfo(id, realName, nickname, email),
                new Agreement(termsOfUseAgreement, personalInformationAgreement),
                new BaseTime(createdTime, updatedTime),
                snsAccounts
        );
    }

    public static User load(Long id, String nickname) {
        return new User(new UserInfo(id, null, nickname, null), null, null, null);
    }

    public void updateNickname(String nickname) {
        this.userInfo.updateNickname(nickname);
    }

    public UserInfo getDeletedUserInfo() {
        return new UserInfo(
                userInfo.getId(),
                userInfo.getMaskedRealName(),
                userInfo.getMaskedNickname(),
                userInfo.getMaskedEmail());
    }
}
