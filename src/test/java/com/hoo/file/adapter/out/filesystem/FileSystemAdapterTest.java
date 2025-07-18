package com.hoo.file.adapter.out.filesystem;

import com.hoo.file.domain.File;
import com.hoo.file.domain.FileF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FileSystemAdapterTest {

    FileSystemAdapter sut;

    @TempDir
    Path tempDir;

    @BeforeEach
    void init() {
        sut = new FileSystemAdapter();
    }

    @Test
    @DisplayName("파일 시스템 저장 테스트")
    void testSave() throws IOException {
        // given
        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());
        MockMultipartFile requestFile = new MockMultipartFile("test.png", "test.png", "image/png", "<<png bin>>".getBytes(StandardCharsets.UTF_8));

        // when
        sut.write(file, requestFile);

        // then
        java.io.File writtenFile = new java.io.File(file.getFileId().getPath());
        assertThat(writtenFile).isReadable();
        assertThat(Files.readAllBytes(writtenFile.toPath())).isEqualTo(requestFile.getBytes());
    }

    @Test
    @DisplayName("파일 지우기 테스트")
    void testEraseFile(@TempDir Path tempDir) throws IOException {
        // given
        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        Files.writeString(javaFile.toPath(), "test file");

        // when
        assertThat(javaFile.exists()).isTrue();
        sut.erase(file);

        // then
        assertThat(javaFile.exists()).isFalse();
    }
}