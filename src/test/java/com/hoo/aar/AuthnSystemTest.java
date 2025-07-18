package com.hoo.aar;

import com.hoo.common.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.admin.application.port.in.user.RegisterUserResult;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.common.adapter.in.web.config.SystemTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.http.client.ClientHttpRequestFactorySettings.Redirects.DONT_FOLLOW;

@SystemTest
@Sql("classpath:/sql/clear.sql")
@Sql("AuthnSystemTest.sql")
public class AuthnSystemTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    JwtDecoder jwtDecoder;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    SnsAccountJpaRepository snsAccountJpaRepository;

    @Autowired
    private ClientHttpRequestFactorySettings clientHttpRequestFactorySettings;

    @Test
    @DisplayName("case1 : 정상 회원가입 플로우")
    void testRegister() {

        /* 1. 사용자 로그인 시도 */

        ResponseEntity<?> loginResponse = whenLogin(1);

        assertThat(loginResponse.getStatusCode().value()).isEqualTo(302);

        /* 2. SNS 계정 임시 저장, 사용자 정보, 임시 토큰 전송 */

        Map<String, String> queryParams = UriComponentsBuilder.fromUri(loginResponse.getHeaders().getLocation()).build().getQueryParams().toSingleValueMap();

        assertThat(snsAccountJpaRepository.findWithUserEntity(SnsDomain.KAKAO, "SNS_ID")).isNotEmpty();
        assertThat(queryParams).containsKey("nickname");
        assertThat(queryParams).containsKey("accessToken");

        String tempAccessToken = queryParams.get("accessToken");
        Jwt jwt = jwtDecoder.decode(tempAccessToken);

        assertThat((String) jwt.getClaim("role")).isEqualTo("TEMP_USER");

        /* 3. 토큰과 응답 바디로 사용자 회원가입 시도 */

        String body = "{\"termsOfUseAgreement\":true, \"personalInformationAgreement\":true}";

        ResponseEntity<?> registResponse = whenRegist(tempAccessToken, body);

        assertThat(registResponse.getStatusCode().value()).isEqualTo(201);

        /* 4. 사용자 회원가입, 사용자 정보, 토큰 전송 */

        RegisterUserResult responseBody = (RegisterUserResult) registResponse.getBody();

        assertThat(userJpaRepository.findAll()).hasSize(1);
        assertThat(responseBody.nickname()).isNotEmpty();
        assertThat(responseBody.accessToken()).isNotEmpty();

        String userAccessToken = responseBody.accessToken();
        Jwt jwt2 = jwtDecoder.decode(userAccessToken);

        assertThat((String) jwt2.getClaim("role")).isEqualTo("USER");
    }

    @Test
    @DisplayName("case2 : 다른 SNS 계정이 DB에 있을 때 신규 SNS 계정으로 회원가입")
    void testNewSnsAccount() {

        /* 1. DB에 존재하지 않는 SNS 계정으로 로그인 시도 */

        ResponseEntity<?> loginResponse = whenLogin(3);

        assertThat(loginResponse.getStatusCode().value()).isEqualTo(302);

        /* 2. SNS 계정 임시 저장, 사용자 정보, 임시 토큰 전송 */

        Map<String, String> queryParams = UriComponentsBuilder.fromUri(loginResponse.getHeaders().getLocation()).build().getQueryParams().toSingleValueMap();

        assertThat(snsAccountJpaRepository.findWithUserEntity(SnsDomain.KAKAO, "SNS_ID_2")).isNotEmpty();
        assertThat(queryParams).containsKey("nickname");
        assertThat(queryParams).containsKey("accessToken");

        String tempAccessToken = queryParams.get("accessToken");
        Jwt jwt = jwtDecoder.decode(tempAccessToken);

        assertThat((String) jwt.getClaim("role")).isEqualTo("TEMP_USER");

        /* 3. 사용자 회원가입 시도 */

        String body = "{\"termsOfUseAgreement\":true, \"personalInformationAgreement\":true}";

        ResponseEntity<?> registResponse = whenRegist(tempAccessToken, body);

        assertThat(registResponse.getStatusCode().value()).isEqualTo(201);

        /* 4. 사용자 회원가입, 사용자 정보, 토큰 전송 */

        RegisterUserResult responseBody = (RegisterUserResult) registResponse.getBody();

        assertThat(userJpaRepository.findById(responseBody.userId())).isNotEmpty();
        assertThat(responseBody.nickname()).isNotEmpty();
        assertThat(responseBody.accessToken()).isNotEmpty();

        String userAccessToken = responseBody.accessToken();
        Jwt jwt2 = jwtDecoder.decode(userAccessToken);

        assertThat((String) jwt2.getClaim("role")).isEqualTo("USER");
    }

    @Test
    @DisplayName("case3 : 이미 DB에 등록된 SNS 계정 재로그인")
    void testAlreadyRegisteredUser() {

        /* 1. 사용자 로그인 시도 */

        ResponseEntity<?> loginResponse = whenLogin(1);

        assertThat(loginResponse.getStatusCode().value()).isEqualTo(302);

        /* 2. SNS 계정 임시 저장, 사용자 정보, 임시 토큰 전송 */

        Map<String, String> queryParams = UriComponentsBuilder.fromUri(loginResponse.getHeaders().getLocation()).build().getQueryParams().toSingleValueMap();

        assertThat(snsAccountJpaRepository.findWithUserEntity(SnsDomain.KAKAO, "SNS_ID")).isNotEmpty();
        assertThat(queryParams).containsKey("nickname");
        assertThat(queryParams).containsKey("accessToken");

        String tempAccessToken = queryParams.get("accessToken");
        Jwt jwt = jwtDecoder.decode(tempAccessToken);

        assertThat((String) jwt.getClaim("role")).isEqualTo("TEMP_USER");

        /* 3. 사용자 회원가입 없이 재로그인 시도 */

        ResponseEntity<?> loginResponse2 = whenLogin(2);

        assertThat(loginResponse2.getStatusCode().value()).isEqualTo(302);

        /* 4. 저장된 SNS 계정 불러옴, 동일한 정보의 토큰 전송 */

        Map<String, String> queryParams2 = UriComponentsBuilder.fromUri(loginResponse.getHeaders().getLocation()).build().getQueryParams().toSingleValueMap();

        snsAccountJpaRepository.findWithUserEntity(SnsDomain.KAKAO, "SNS_ID"); // SNS Entity 중복여부 확인
        String tempAccessToken2 = queryParams2.get("accessToken");
        Jwt jwt2 = jwtDecoder.decode(tempAccessToken2);

        assertThat(jwt.getClaims()).usingRecursiveComparison()
                .ignoringFields("exp", "iat").isEqualTo(jwt2.getClaims());
    }

    private ResponseEntity<?> whenLogin(long userId) {
        HttpEntity<?> request = getHttpEntity(null, null);

        return restTemplate
                .withRequestFactorySettings(
                        clientHttpRequestFactorySettings.withRedirects(DONT_FOLLOW))
                .exchange(
                        "/mock/authn/login/" + userId,
                        HttpMethod.GET,
                        request,
                        Void.class);
    }

    private ResponseEntity<?> whenRegist(String accessToken, String body) {
        HttpEntity<?> request = getHttpEntity(accessToken, body);

        return restTemplate.exchange(
                "/aar/authn/regist",
                HttpMethod.POST,
                request,
                RegisterUserResult.class);
    }

    private HttpEntity<?> getHttpEntity(String accessToken, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(body, headers);
    }
}
