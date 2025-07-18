package com.hoo.aar.adapter.out.mail;

import com.hoo.aar.application.port.out.mail.SendAuthnCodePort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailAdapter implements SendAuthnCodePort {

    private final JavaMailSender mailSender;

    @Override
    public void sendAuthnCode(String address, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setSubject("HOO 메일인증 요청");
        msg.setTo(address);
        msg.setText(String.format("인증번호는 [%s]입니다.", message));

        mailSender.send(msg);
    }
}
