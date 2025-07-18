package com.hoo.aar.application.service.authn;

import com.hoo.aar.application.port.in.authn.VerifyEmailAuthnCodeResult;
import com.hoo.aar.application.port.out.cache.LoadEmailAuthnCodePort;
import com.hoo.aar.application.port.out.cache.SaveEmailAuthnStatePort;
import com.hoo.aar.application.service.AarErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerifyEmailAuthnCodeServiceTest {

    LoadEmailAuthnCodePort loadEmailAuthnCodePort = mock();
    SaveEmailAuthnStatePort saveEmailAuthnStatePort = mock();
    VerifyEmailAuthnCodeService sut = new VerifyEmailAuthnCodeService(loadEmailAuthnCodePort, saveEmailAuthnStatePort);

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(sut, "authnStatusTTLSecond", 3600);
    }

    @Test
    @DisplayName("이메일 인증코드 검증 서비스")
    void testEmailCodeVerifyService() {
        // given
        String email = "test@example.com";
        String code = "123456";

        // when

        // 인증번호가 비어있을 경우
        when(loadEmailAuthnCodePort.loadAuthnCodeByEmail(email)).thenReturn(null);
        assertThatThrownBy(() -> sut.verify(email, code)).hasMessage(AarErrorCode.EMAIL_CODE_AUTHENTICATION_FAILED.getMessage());

        // 인증번호 다를 경우
        when(loadEmailAuthnCodePort.loadAuthnCodeByEmail(email)).thenReturn("654321");
        assertThatThrownBy(() -> sut.verify(email, code)).hasMessage(AarErrorCode.EMAIL_CODE_AUTHENTICATION_FAILED.getMessage());

        // 정상 인증번호
        when(loadEmailAuthnCodePort.loadAuthnCodeByEmail(email)).thenReturn(code);
        VerifyEmailAuthnCodeResult result = sut.verify(email, code);

        // then
        verify(saveEmailAuthnStatePort, times(1)).saveAuthenticated(email, Duration.ofSeconds(3600));
        assertThat(result.message()).isEqualTo("이메일 인증에 성공했습니다.");
        assertThat(result.ttl()).isEqualTo(3600);
    }

}