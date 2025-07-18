package com.hoo.aar.application.service.authn;

import com.hoo.aar.application.port.in.authn.CreateEmailAuthnCodeResult;
import com.hoo.aar.application.port.out.cache.SaveEmailAuthnCodePort;
import com.hoo.aar.application.port.out.mail.SendAuthnCodePort;
import com.hoo.aar.application.service.AarErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateEmailAuthnCodeServiceTest {

    SaveEmailAuthnCodePort saveEmailAuthnCodePort = mock();
    SendAuthnCodePort sendAuthnCodePort = mock();

    CreateEmailAuthnCodeService sut = new CreateEmailAuthnCodeService(saveEmailAuthnCodePort, sendAuthnCodePort);

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(sut, "authnCodeTTLSecond", 300);
    }

    @Test
    @DisplayName("이메일 인증 코드 생성 서비스")
    void testCreateEmailAuthnCodeService() {
        // given
        String badEmail = "badEmail";
        String mail = "test@example.com";

        // when
        assertThatThrownBy(() -> sut.create(badEmail)).hasMessage(AarErrorCode.INVALID_EMAIL_ADDRESS.getMessage());
        CreateEmailAuthnCodeResult result = sut.create(mail);

        // then
        verify(saveEmailAuthnCodePort, times(1)).saveEmailAuthnCode(anyString(), anyString(), any());
        verify(sendAuthnCodePort, times(1)).sendAuthnCode(anyString(), anyString());
        assertThat(result.message()).matches("^\\[이메일 : [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}]로 인증코드 전송이 완료되었습니다\\.$");
        assertThat(result.ttl()).isEqualTo(300);
    }

}