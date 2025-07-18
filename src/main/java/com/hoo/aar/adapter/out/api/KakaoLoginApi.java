package com.hoo.aar.adapter.out.api;

import com.hoo.aar.application.port.in.authn.OAuth2Dto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoLoginApi {

    private final KakaoProperties kakaoProperties;

    public void unlink(String snsId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, getKakaoAuthHeader());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target_id_type", "user_id");
        body.add("target_id", snsId);

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        ResponseEntity<OAuth2Dto.KakaoUnlinkResponse> response = restTemplate.postForEntity(kakaoProperties.getUnlinkUrl(), entity, OAuth2Dto.KakaoUnlinkResponse.class);

        log.info("KAKAO 계정 연동 해제 : {}", response.getBody().id());
    }

    private String getKakaoAuthHeader() {
        return "KakaoAK " + kakaoProperties.getAdminKey();
    }
}
