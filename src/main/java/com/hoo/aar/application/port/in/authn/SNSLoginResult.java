package com.hoo.aar.application.port.in.authn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public record SNSLoginResult(
        String nickname,
        String accessToken,
        String provider,
        Boolean isFirstLogin
) {

    public static SNSLoginResult from(Map<String, Object> attributes) {
        return new SNSLoginResult(
                (String) attributes.get("nickname"),
                (String) attributes.get("accessToken"),
                (String) attributes.get("provider"),
                (Boolean) attributes.get("isFirstLogin"));
    }

    @JsonIgnore
    public Map<String, Object> getAttributes() {

        Map<String, Object> ret = new HashMap<>();

        ret.put("nickname", nickname);
        ret.put("accessToken", accessToken);
        ret.put("provider", provider);
        ret.put("isFirstLogin", isFirstLogin);

        return ret;
    }
}
