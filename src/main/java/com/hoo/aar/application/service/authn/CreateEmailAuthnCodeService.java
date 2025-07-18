package com.hoo.aar.application.service.authn;

import com.hoo.aar.application.port.in.authn.CreateEmailAuthnCodeResult;
import com.hoo.aar.application.port.in.authn.CreateEmailAuthnCodeUseCase;
import com.hoo.aar.application.port.out.cache.SaveEmailAuthnCodePort;
import com.hoo.aar.application.port.out.mail.SendAuthnCodePort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CreateEmailAuthnCodeService implements CreateEmailAuthnCodeUseCase {

    private final SaveEmailAuthnCodePort saveEmailAuthnCodePort;
    private final SendAuthnCodePort sendAuthnCodePort;

    @Value("${security.email-authn-code-ttl:600}")
    private Integer authnCodeTTLSecond;

    @Override
    public CreateEmailAuthnCodeResult create(String email) {
        verifyEmail(email);

        String code = createCode();
        sendAuthnCodePort.sendAuthnCode(email, code);
        saveEmailAuthnCodePort.saveEmailAuthnCode(email, code, Duration.ofSeconds(authnCodeTTLSecond));

        return new CreateEmailAuthnCodeResult(String.format("[이메일 : %s]로 인증코드 전송이 완료되었습니다.", email),
                authnCodeTTLSecond);
    }

    private void verifyEmail(String email) {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
            throw new AarException(AarErrorCode.INVALID_EMAIL_ADDRESS);
    }

    private String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; i++) builder.append(random.nextInt(10));

            return builder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
