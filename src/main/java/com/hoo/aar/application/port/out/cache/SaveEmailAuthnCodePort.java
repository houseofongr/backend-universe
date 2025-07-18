package com.hoo.aar.application.port.out.cache;

import java.time.Duration;

public interface SaveEmailAuthnCodePort {
    void saveEmailAuthnCode(String email, String code, Duration ttl);
}
