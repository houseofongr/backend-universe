package com.hoo.aar.adapter.out.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KakaoProperties {

    private final String unlinkUrl;
    private final String clientId;
    private final String clientSecret;
    private final String adminKey;

    public KakaoProperties(
            @Value("${security.oauth2.kakao.unlink-url}") String unlinkUrl,
            @Value("${security.oauth2.kakao.client-id}") String clientId,
            @Value("${security.oauth2.kakao.client-secret}") String clientSecret,
            @Value("${security.oauth2.kakao.admin-key}") String adminKey) {
        this.unlinkUrl = unlinkUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.adminKey = adminKey;
    }

}
