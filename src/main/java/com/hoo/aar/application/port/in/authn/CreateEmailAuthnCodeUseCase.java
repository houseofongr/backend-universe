package com.hoo.aar.application.port.in.authn;

public interface CreateEmailAuthnCodeUseCase {
    CreateEmailAuthnCodeResult create(String email);
}
