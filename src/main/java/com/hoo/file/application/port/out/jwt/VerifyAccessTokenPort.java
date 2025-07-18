package com.hoo.file.application.port.out.jwt;

public interface VerifyAccessTokenPort {
    void verifyImageToken(String accessToken);

    boolean verifyAudioToken(String accessToken, Long fileId);
}
