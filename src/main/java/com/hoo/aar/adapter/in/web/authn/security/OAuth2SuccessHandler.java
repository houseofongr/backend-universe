package com.hoo.aar.adapter.in.web.authn.security;

import com.hoo.aar.application.port.in.authn.SNSLoginResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${security.frontend-redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SNSLoginResult dto = SNSLoginResult.from(
                ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes());

        String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("nickname", URLEncoder.encode(dto.nickname(), StandardCharsets.UTF_8))
                .queryParam("accessToken", dto.accessToken())
                .queryParam("provider", URLEncoder.encode(dto.provider().toLowerCase(), StandardCharsets.UTF_8))
                .queryParam("isFirstLogin", String.valueOf(dto.isFirstLogin()))
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
