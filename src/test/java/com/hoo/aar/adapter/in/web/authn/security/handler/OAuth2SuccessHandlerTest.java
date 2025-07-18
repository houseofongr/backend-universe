package com.hoo.aar.adapter.in.web.authn.security.handler;

import com.hoo.aar.adapter.in.web.authn.security.OAuth2SuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2SuccessHandlerTest {

    String redirectUri = "REDIRECT_URI";
    OAuth2SuccessHandler sut;

    @BeforeEach
    void init() {
        sut = new OAuth2SuccessHandler();
        ReflectionTestUtils.setField(sut, "redirectUri", redirectUri);
    }

    @Test
    @DisplayName("OAuth2SuccessHandler 정상 동작 테스트")
    void testSuccessHandler() throws ServletException, IOException {
        // given
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", "leaf");
        map.put("accessToken", "ACCESS_TOKEN");
        map.put("provider", "kakao");
        map.put("isFirstLogin", false);
        DefaultOAuth2User principal = new DefaultOAuth2User(null, map, "nickname");
        HttpServletResponse response = new MockHttpServletResponse();
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null);

        // when
        sut.onAuthenticationSuccess(null, response, authentication);

        // then
        assertThat(response.getHeader(HttpHeaders.LOCATION)).contains(redirectUri);
        assertThat(response.getHeader(HttpHeaders.LOCATION)).contains("nickname", "accessToken", "provider", "isFirstLogin");
        assertThat(response.getStatus()).isEqualTo(302);
    }
}