package com.hoo.aar.application.service.authn;

import com.hoo.aar.application.port.in.authn.VerifyEmailAuthnCodeResult;
import com.hoo.aar.application.port.in.authn.VerifyEmailAuthnCodeUseCase;
import com.hoo.aar.application.port.out.cache.LoadEmailAuthnCodePort;
import com.hoo.aar.application.port.out.cache.SaveEmailAuthnStatePort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VerifyEmailAuthnCodeService implements VerifyEmailAuthnCodeUseCase {

    private final LoadEmailAuthnCodePort loadEmailAuthnCodePort;
    private final SaveEmailAuthnStatePort saveEmailAuthnStatePort;

    @Value("${security.email-authn-status-ttl:6000}")
    private Integer authnStatusTTLSecond;

    @Override
    public VerifyEmailAuthnCodeResult verify(String email, String code) {
        String codeInCache = loadEmailAuthnCodePort.loadAuthnCodeByEmail(email);
        if (!Objects.equals(codeInCache, code)) throw new AarException(AarErrorCode.EMAIL_CODE_AUTHENTICATION_FAILED);

        saveEmailAuthnStatePort.saveAuthenticated(email, Duration.ofSeconds(authnStatusTTLSecond));

        return new VerifyEmailAuthnCodeResult(
                "이메일 인증에 성공했습니다.",
                authnStatusTTLSecond);
    }
}
