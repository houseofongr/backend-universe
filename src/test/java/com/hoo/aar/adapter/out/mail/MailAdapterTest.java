package com.hoo.aar.adapter.out.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MailAdapterTest {

    JavaMailSender mailSender = mock();
    MailAdapter sut = new MailAdapter(mailSender);
    
    @Test
    @DisplayName("인증번호 전송 테스트")
    void testSendEmailAuthnCode() {
        // given
        String email = "test@example.com";
        String code = "123456";

        // when
        sut.sendAuthnCode(email, code);

        // then
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertThat(sentMessage.getSubject()).isEqualTo("HOO 메일인증 요청");
        assertThat(sentMessage.getTo()[0]).isEqualTo(email);
        assertThat(sentMessage.getText()).isEqualTo("인증번호는 [123456]입니다.");
    }

}