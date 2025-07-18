package com.hoo.aar.application.port.in.user;

public record CreateBusinessUserCommand(
        String email,
        String password,
        String nickname,
        Boolean termsOfUseAgreement,
        Boolean personalInformationAgreement
) {
}
