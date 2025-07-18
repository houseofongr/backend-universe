package com.hoo.aar.application.port.in.authn;

public record VerifyEmailAuthnCodeResult(
        String message,
        Integer ttl
) {
}
