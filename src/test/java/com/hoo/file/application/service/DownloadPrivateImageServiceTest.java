package com.hoo.file.application.service;

import com.hoo.file.application.port.in.DownloadImageUseCase;
import com.hoo.file.application.port.out.jwt.VerifyAccessTokenPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DownloadPrivateImageServiceTest {

    DownloadPrivateImageService sut;

    VerifyAccessTokenPort verifyAccessTokenPort;
    DownloadImageUseCase downloadImageUseCase;

    @BeforeEach
    void init() {
        verifyAccessTokenPort = mock();
        downloadImageUseCase = mock();
        sut = new DownloadPrivateImageService(verifyAccessTokenPort, downloadImageUseCase);
    }

    @Test
    @DisplayName("프라이빗 이미지 획득 테스트")
    void testGetPrivateImage() {
        // given
        String goodToken = "good";
        String badToken = "bad";
        Long fileId = 1L;

        // when
        doThrow(new JwtException("bad token")).when(verifyAccessTokenPort).verifyImageToken(badToken);
        assertThatThrownBy(() -> sut.privateDownload(badToken, fileId)).hasMessage(FileErrorCode.INVALID_AUTHORITY.getMessage());

        sut.privateDownload(goodToken, fileId);

        // then
        verify(downloadImageUseCase, times(1)).privateDownload(fileId);
    }
}