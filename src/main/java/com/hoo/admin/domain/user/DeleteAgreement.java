package com.hoo.admin.domain.user;

import lombok.Getter;

@Getter
public class DeleteAgreement {

    private final Boolean termsOfDeletionAgreement;
    private final Boolean personalInformationDeletionAgreement;

    public DeleteAgreement(Boolean termsOfDeletionAgreement, Boolean personalInformationDeletionAgreement) {
        this.termsOfDeletionAgreement = termsOfDeletionAgreement;
        this.personalInformationDeletionAgreement = personalInformationDeletionAgreement;
    }

}
