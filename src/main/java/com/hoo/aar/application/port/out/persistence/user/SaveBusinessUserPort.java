package com.hoo.aar.application.port.out.persistence.user;

public interface SaveBusinessUserPort {
    Long save(String email, String encryptedPassword, String nickname, Boolean termsOfUseAgreement, Boolean personalInformationAgreement);
}
