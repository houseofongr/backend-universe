package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.out.database.FindFilePort;
import com.hoo.file.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DownloadServiceTest {

    DownloadService sut;

    FindFilePort findFilePort;

    @TempDir
    Path tempDir;

    @BeforeEach
    void init() {
        findFilePort = mock();
        sut = new DownloadService(findFilePort);
    }

    @Test
    @DisplayName("퍼블릭 이미지 로드 테스트")
    void testLoadImage() throws IOException {
        // given
        Long fileId = 1L;
        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        Files.writeString(javaFile.toPath(), "test file");

        // when
        when(findFilePort.load(fileId)).thenReturn(Optional.of(file));
        DownloadFileResult inlineResult = sut.load(fileId, FileType.IMAGE, Authority.PUBLIC_FILE_ACCESS, false);
        DownloadFileResult attachmentResult = sut.load(fileId, FileType.IMAGE, Authority.PUBLIC_FILE_ACCESS, true);

        // then
        assertThat(inlineResult.disposition()).contains("inline;").contains(file.getFileId().getFileSystemName());
        assertThat(inlineResult.resource().getContentAsByteArray().length).isEqualTo(9);

        assertThat(attachmentResult.disposition()).contains("attachment;").contains(file.getFileId().getRealFileName());
        assertThat(attachmentResult.resource().getContentAsByteArray().length).isEqualTo(9);
    }

    @Test
    @DisplayName("퍼블릭 오디오 로드 테스트")
    void testDownloadImage() throws IOException {
        // given
        Long fileId = 1L;
        File file = File.create(FileId.create(tempDir.toString(), Authority.PUBLIC_FILE_ACCESS, FileType.AUDIO, "test.mp3", "test.mp3"), FileStatus.CREATED, null, new FileSize(1234L, 10000L));
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        Files.writeString(javaFile.toPath(), "test file");

        // when
        when(findFilePort.load(fileId)).thenReturn(Optional.of(file));
        DownloadFileResult inlineResult = sut.load(fileId, FileType.AUDIO, Authority.PUBLIC_FILE_ACCESS, false);
        DownloadFileResult attachmentResult = sut.load(fileId, FileType.AUDIO, Authority.PUBLIC_FILE_ACCESS, true);

        // then
        assertThat(inlineResult.disposition()).contains("inline;").contains(file.getFileId().getFileSystemName());
        assertThat(inlineResult.resource().getContentAsByteArray().length).isEqualTo(9);

        assertThat(attachmentResult.disposition()).contains("attachment;").contains(file.getFileId().getRealFileName());
        assertThat(attachmentResult.resource().getContentAsByteArray().length).isEqualTo(9);
    }

    @Test
    @DisplayName("프라이빗 이미지 다운로드 테스트")
    void testLoadPrivate() throws IOException {
        // given
        Long fileId = 1L;
        File file = File.create(FileId.create(tempDir.toString(), Authority.ALL_PRIVATE_IMAGE_ACCESS, FileType.IMAGE, "test.png", "test.png"), FileStatus.CREATED, null, new FileSize(1234L, 10000L));
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        Files.writeString(javaFile.toPath(), "test file");

        // when
        when(findFilePort.load(fileId)).thenReturn(Optional.of(file));
        DownloadFileResult result = sut.load(fileId, FileType.IMAGE, Authority.ALL_PRIVATE_IMAGE_ACCESS, false);

        // then
        assertThat(result.disposition()).contains("inline;").contains(file.getFileId().getFileSystemName());
        assertThat(result.resource().getContentAsString(StandardCharsets.UTF_8)).isEqualTo("test file");
    }

    @Test
    @DisplayName("프라이빗 오디오 다운로드 테스트")
    void testDownloadPrivate() throws IOException {
        // given
        Long fileId = 1L;
        File file = File.create(FileId.create(tempDir.toString(), Authority.ALL_PRIVATE_AUDIO_ACCESS, FileType.AUDIO, "test.mp3", "test.mp3"), FileStatus.CREATED, null, new FileSize(1234L, 10000L));
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        Files.writeString(javaFile.toPath(), "test file");

        // when
        when(findFilePort.load(fileId)).thenReturn(Optional.of(file));
        DownloadFileResult result = sut.load(fileId, FileType.AUDIO, Authority.ALL_PRIVATE_AUDIO_ACCESS, false);

        // then
        assertThat(result.disposition()).contains("inline;").contains(file.getFileId().getFileSystemName());
        assertThat(result.mediaType()).isEqualTo(MediaType.parseMediaType("audio/mpeg"));
        assertThat(result.resource().getContentAsString(StandardCharsets.UTF_8)).isEqualTo("test file");
    }
}