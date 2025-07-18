package com.hoo.aar.application.port.in.authn;

public interface VerifyEmailAuthnCodeUseCase {
    VerifyEmailAuthnCodeResult verify(String email, String code);
}
