package com.hoo.aar.application.port.in.authn;

public record OAuth2Dto() {
    public record KakaoUserInfo(
            String id,
            KakaoAccount kakao_account
    ) {
        public record KakaoAccount(
                String email,
                Boolean has_email,
                Boolean is_email_valid,
                Boolean is_email_verified,
                KakaoAccount.Profile profile
        ) {
            public record Profile(
                    String nickname,
                    Boolean is_default_nickname
            ) {
            }
        }
    }

    public record KakaoUnlinkResponse(
            Long id
    ) {

    }
}
