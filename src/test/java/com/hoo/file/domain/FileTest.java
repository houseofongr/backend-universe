package com.hoo.file.domain;

import com.hoo.common.domain.Authority;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.FileSizeLimitExceedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FileTest {

    @Test
    @DisplayName("빈 이미지파일 생성 테스트")
    void testCreate(@TempDir Path tempDir) throws FileSizeLimitExceedException, IOException, FileExtensionMismatchException {
        // given
        String baseDir = tempDir.toString();
        Authority authority = Authority.PUBLIC_FILE_ACCESS;
        FileType fileType = FileType.IMAGE;
        String fileName = "test.png";

        FileId fileId = FileId.create(baseDir, authority, fileType, fileName, fileName);

        // when
        File file = File.create(fileId, FileStatus.CREATED, OwnerId.empty(), new FileSize(10000L, 100000L));
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();

        // then
        assertThat(javaFile).isReadable();
        assertThat(javaFile).isFile();
    }
}