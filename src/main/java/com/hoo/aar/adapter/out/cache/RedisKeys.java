package com.hoo.aar.adapter.out.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKeys {
    EMAIL_AUTHN_CODE_PREFIX("email-authn-code:"),
    EMAIL_AUTHN_STATUS_PREFIX("email-authenticated:");

    private final String key;
}
