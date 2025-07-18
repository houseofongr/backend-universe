package com.hoo.aar.application.port.in.authn;

public record CreateEmailAuthnCodeResult(
        String message,
        Integer ttl
) {
}
