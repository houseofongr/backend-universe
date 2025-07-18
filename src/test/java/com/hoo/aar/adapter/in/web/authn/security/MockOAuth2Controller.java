package com.hoo.aar.adapter.in.web.authn.security;

import com.hoo.aar.adapter.out.jwt.JwtAdapter;
import com.hoo.admin.domain.user.UserId;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsAccountId;
import com.hoo.admin.domain.user.snsaccount.SnsAccountInfo;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

@RestController
@RequiredArgsConstructor
public class MockOAuth2Controller {

    @Value("${security.frontend-redirect-uri}")
    private String redirectUri;

    private final JwtAdapter jwtAdapter;

    @GetMapping("/mock/authn/login/{snsAccountId}")
    void mockLogin(HttpServletResponse response, @PathVariable Long snsAccountId) throws IOException {

        if (snsAccountId == 1L) {

            SnsAccount snsAccount = mock();

            when(snsAccount.getSnsAccountId()).thenReturn(new SnsAccountId(1L, SnsDomain.KAKAO, "SNS_ID"));
            when(snsAccount.getSnsAccountInfo()).thenReturn(new SnsAccountInfo("남상엽", "leaf", "test@example.com"));
            when(snsAccount.getUserId()).thenReturn(null);

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode(snsAccount.getSnsAccountInfo().getNickname(), StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtAdapter.issueAccessToken(snsAccount))
                    .queryParam("provider", snsAccount.getSnsAccountId().getSnsDomain())
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);

        } else if (snsAccountId == 2L) {

            SnsAccount snsAccount = mock();

            when(snsAccount.getSnsAccountId()).thenReturn(new SnsAccountId(1L, SnsDomain.KAKAO, "SNS_ID"));
            when(snsAccount.getSnsAccountInfo()).thenReturn(new SnsAccountInfo("남상엽", "leaf", "test@example.com"));
            when(snsAccount.getUserId()).thenReturn(new UserId(1L));

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode(snsAccount.getSnsAccountInfo().getNickname(), StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtAdapter.issueAccessToken(snsAccount))
                    .queryParam("provider", snsAccount.getSnsAccountId().getSnsDomain())
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);

        } else if (snsAccountId == 3L) {

            SnsAccount snsAccount = mock();

            when(snsAccount.getSnsAccountId()).thenReturn(new SnsAccountId(2L, SnsDomain.KAKAO, "SNS_ID_2"));
            when(snsAccount.getSnsAccountInfo()).thenReturn(new SnsAccountInfo("남엽돌", "leaf", "test@example.com"));
            when(snsAccount.getUserId()).thenReturn(new UserId(null));

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode(snsAccount.getSnsAccountInfo().getNickname(), StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtAdapter.issueAccessToken(snsAccount))
                    .queryParam("provider", snsAccount.getSnsAccountId().getSnsDomain())
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);
        } else if (snsAccountId == 4L) {

            SnsAccount snsAccount = mock();

            when(snsAccount.getSnsAccountId()).thenReturn(new SnsAccountId(2L, SnsDomain.KAKAO, "SNS_ID_2"));
            when(snsAccount.getSnsAccountInfo()).thenReturn(new SnsAccountInfo("남엽돌", "leaf", "test@example.com"));
            when(snsAccount.getUserId()).thenReturn(new UserId(1L));

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode(snsAccount.getSnsAccountInfo().getNickname(), StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtAdapter.issueAccessToken(snsAccount))
                    .queryParam("provider", snsAccount.getSnsAccountId().getSnsDomain())
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);

        } else throw new UnsupportedOperationException("SNS Account Id 허용범위 초과");
    }
}
