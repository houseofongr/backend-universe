package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.port.out.jwt.IssueAccessTokenPort;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccessTokenController {

    private final IssueAccessTokenPort issueAccessTokenPort;

    @GetMapping("/access-token")
    public ResponseEntity<?> getAccessToken() {
        SnsAccount account = SnsAccount.load(1L, SnsDomain.KAKAO, "SNS_ID", "남상엽", "leaf", "test@example.com", ZonedDateTime.now(), ZonedDateTime.now(), 1L);
        return ResponseEntity.ok(Map.of("accessToken", issueAccessTokenPort.issueAccessToken(account)));
    }
}
