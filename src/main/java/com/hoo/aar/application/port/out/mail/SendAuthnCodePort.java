package com.hoo.aar.application.port.out.mail;

public interface SendAuthnCodePort {
    void sendAuthnCode(String emailAddress, String message);
}
