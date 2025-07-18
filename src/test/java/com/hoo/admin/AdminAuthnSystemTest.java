package com.hoo.admin;

import com.hoo.admin.adapter.in.web.authn.security.AdminSecurityConfig;
import com.hoo.common.adapter.in.web.config.SystemTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.http.client.ClientHttpRequestFactorySettings.Redirects.DONT_FOLLOW;

@SystemTest
@Sql("classpath:/sql/clear.sql")
@Sql("AdminAuthnSystemTest.sql")
@Import(AdminSecurityConfig.class)
public class AdminAuthnSystemTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ClientHttpRequestFactorySettings clientHttpRequestFactorySettings;

    @Test
    @DisplayName("case1 : 정상 관리자 로그인 테스트")
    void testAdminLogin() {

        /* 1. 1차 로그인(Username, Password) 시도 */

        String username = "testAdmin";
        String password = "testPassword";

        ResponseEntity<?> usernameAndPasswordLogin = whenUsernameAndPasswordLogin(username, password);

        assertThat(usernameAndPasswordLogin.getStatusCode().value()).isEqualTo(302);
        assertThat(usernameAndPasswordLogin.getHeaders().getLocation().toString()).contains("/login?form-success");

        /* 2. 2차 로그인(SNS) 시도 */

        ResponseEntity<?> snsLogin = whenSnsLogin();

        assertThat(snsLogin.getStatusCode().value()).isEqualTo(302);
        assertThat(snsLogin.getHeaders().getLocation().toString()).contains("/admin/authn/code/callback");
    }

    private String getCsrfToken() {
        return restTemplate.getForObject("/csrf", String.class);
    }

    private ResponseEntity<?> whenUsernameAndPasswordLogin(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("password", password);
        params.add("username", username);
        HttpEntity<?> request = new HttpEntity<>(params, headers);

        return restTemplate
                .withRequestFactorySettings(
                        clientHttpRequestFactorySettings.withRedirects(DONT_FOLLOW))
                .exchange("/admin/authn/login", HttpMethod.POST, request, Void.class);
    }

    private ResponseEntity<?> whenSnsLogin() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<?> request = new HttpEntity<>(headers);

        return restTemplate
                .withRequestFactorySettings(
                        clientHttpRequestFactorySettings.withRedirects(DONT_FOLLOW))
                .exchange(
                        "/admin/authn/login/sns/admin-kakao",
                        HttpMethod.GET,
                        request,
                        Void.class);
    }
}
