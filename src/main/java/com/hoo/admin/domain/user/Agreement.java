package com.hoo.admin.domain.user;

import lombok.Getter;

@Getter
public class Agreement {
    private final Boolean termsOfUseAgreement;
    private final Boolean personalInformationAgreement;

    public Agreement(Boolean termsOfUseAgreement, Boolean personalInformationAgreement) {
        this.termsOfUseAgreement = termsOfUseAgreement;
        this.personalInformationAgreement = personalInformationAgreement;
    }
}
