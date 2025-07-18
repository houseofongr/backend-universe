package com.hoo.aar.application.port.in.user;

import java.util.List;

public record SearchMyInfoResult(
        String nickname,
        String email,
        String registeredDate,
        Boolean termsOfUseAgreement,
        Boolean personalInformationAgreement,
        Integer myHomeCount,
        Integer mySoundSourceCount,
        List<SnsAccountInfo> snsAccountInfos
) {

    public record SnsAccountInfo(
            String domain,
            String email
    ) {

    }
}
