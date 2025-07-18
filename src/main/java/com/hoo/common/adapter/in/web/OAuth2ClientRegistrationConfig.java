package com.hoo.common.adapter.in.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class OAuth2ClientRegistrationConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            @Value("${security.oauth2.kakao.client-id}") String clientId,
            @Value("${security.oauth2.kakao.client-secret}") String clientSecret,
            @Value("${security.backend-base-uri}") String baseUri,
            @Value("${spring.security.oauth2.client.provider.kakao.ownerization-uri}") String ownerizationUri,
            @Value("${spring.security.oauth2.client.provider.kakao.token-uri}") String tokenUri,
            @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}") String userInfoUri,
            @Value("${spring.security.oauth2.client.provider.kakao.user-name-attribute}") String userNameAttribute
    ) {
        return new InMemoryClientRegistrationRepository(this.adminKakaoRegistration(clientId, clientSecret, baseUri, ownerizationUri, tokenUri, userInfoUri, userNameAttribute), this.aarKakaoRegistration(clientId, clientSecret, baseUri, ownerizationUri, tokenUri, userInfoUri, userNameAttribute));
    }

    private ClientRegistration adminKakaoRegistration(String clientId, String clientSecret, String baseUri, String ownerizationUri, String tokenUri, String userInfoUri, String userNameAttribute) {
        return ClientRegistration.withRegistrationId("admin-kakao")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .ownerizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(baseUri + "/admin/authn/code/callback")
                .ownerizationUri(ownerizationUri)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .userNameAttributeName(userNameAttribute)
                .clientName("Kakao")
                .build();
    }

    private ClientRegistration aarKakaoRegistration(String clientId, String clientSecret, String baseUri, String ownerizationUri, String tokenUri, String userInfoUri, String userNameAttribute) {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .ownerizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(baseUri + "/aar/authn/code/callback")
                .ownerizationUri(ownerizationUri)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .userNameAttributeName(userNameAttribute)
                .clientName("Kakao")
                .build();
    }

}
