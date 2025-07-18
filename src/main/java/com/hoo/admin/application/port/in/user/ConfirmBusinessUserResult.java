package com.hoo.admin.application.port.in.user;

public record ConfirmBusinessUserResult(
        String message,
        Long userId,
        String email,
        String nickname,
        Boolean termsOfUseAgreement,
        Boolean personalInformationAgreement
) {
}
