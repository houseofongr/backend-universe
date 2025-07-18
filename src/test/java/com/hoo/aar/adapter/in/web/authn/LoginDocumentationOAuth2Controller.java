package com.hoo.aar.adapter.in.web.authn;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class LoginDocumentationOAuth2Controller {

    @GetMapping("/aar/authn/login/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String redirectUrl = UriComponentsBuilder.fromUriString("https://archiveofongr.site/login/auth")
                .queryParam("nickname", URLEncoder.encode("남상엽", StandardCharsets.UTF_8))
                .queryParam("accessToken", "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhYXItYmFja2VuZCIsInN1YiI6IuuCqOyDgeyXvSIsInJvbGUiOiJURU1QX1VTRVIiLCJzbnNJZCI6IjM4NTk0NzYwMjkiLCJleHAiOjE3MzU3MjI4MjIsInVzZXJJZCI6LTF9.aLJFkMUsfRhFxEWiWdJV0D0gytj1NrZYkw9UIAfDjcQ")
                .queryParam("provider", "kakao")
                .queryParam("isFirstLogin", "false")
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
