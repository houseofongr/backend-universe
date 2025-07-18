package com.hoo.admin.application.port.in.user;

public record DeleteUserCommand(
        Boolean termsOfDeletionAgreement,
        Boolean personalInformationDeletionAgreement
) {
}
