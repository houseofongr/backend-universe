package com.hoo.file.domain;

import com.hoo.common.domain.Authority;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.file.domain.exception.IllegalFileTypeDirException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FileIdTest {

    @Test
    @DisplayName("생성 시 Authority와 fileType에 맞춰서 경로 생성")
    void testAddPath() throws FileExtensionMismatchException {
        // given
        String baseDir = "/tmp";
        Authority authority = Authority.PUBLIC_FILE_ACCESS;
        FileType fileType = FileType.IMAGE;
        String fileName = "test.png";

        // when
        FileId fileId = FileId.create(baseDir, authority, fileType, fileName, fileName);

        // then
        assertThat(fileId.getPath()).isEqualTo("/tmp/public/images/test.png");
        assertThat(fileId.getFilePath()).isEqualTo("file:/tmp/public/images/test.png");
    }

    @Test
    @DisplayName("생성 시 directory 뒤에 '/' 가 있으면 제거해주기")
    void testAddSlash() throws FileExtensionMismatchException {
        // given
        String baseDir = "/tmp/";
        Authority authority = Authority.PUBLIC_FILE_ACCESS;
        FileType fileType = FileType.IMAGE;
        String fileName = "test.png";

        // when
        FileId fileId = FileId.create(baseDir, authority, fileType, fileName, fileName);

        // then
        assertThat(fileId.getPath()).isEqualTo("/tmp/public/images/test.png");
    }

    @Test
    @DisplayName("파일 디렉토리 생성 테스트")
    void testGetDirectory() throws FileExtensionMismatchException {
        // given
        String baseDir = "/tmp/";
        Authority authority = Authority.PUBLIC_FILE_ACCESS;
        FileType fileType = FileType.IMAGE;
        String fileName = "test.png";

        // when
        FileId fileId = FileId.create(baseDir, authority, fileType, fileName, fileName);

        // then
        assertThat(fileId.getDirectory()).isEqualTo("/tmp/public/images");
    }

    @Test
    @DisplayName("이미지 확장자 테스트")
    void testImageExtension() throws FileExtensionMismatchException {
        String baseDir = "/tmp/";
        Authority authority = Authority.PUBLIC_FILE_ACCESS;
        FileType fileType = FileType.IMAGE;

        FileId.create(baseDir, authority, fileType, "test.png", "file-system-name.png");
        FileId.create(baseDir, authority, fileType, "test.jpg", "file-system-name.png");
        FileId.create(baseDir, authority, fileType, "test.jpeg", "file-system-name.png");
        FileId.create(baseDir, authority, fileType, "test.svg", "file-system-name.png");
        FileId.create(baseDir, authority, fileType, "test.gif", "file-system-name.png");

        assertThatThrownBy(() -> FileId.create(baseDir, authority, fileType, "test", "file-system-name")).isInstanceOf(FileExtensionMismatchException.class);
        assertThatThrownBy(() -> FileId.create(baseDir, authority, fileType, "test.sh", "file-system-name.sh")).isInstanceOf(FileExtensionMismatchException.class);
    }

    @Test
    @DisplayName("파일 경로와 이름으로 ID 생성 테스트")
    void testLoadFileIdByNameAndDir() throws FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {
        // given
        String parentDir = "/test/public/images";
        String fileName = "test.png";
        String fileSystemName = "test-1234.png";

        // when
        FileId fileId = FileId.load(parentDir, fileName, fileSystemName);

        // then
        assertThat(fileId.getRealFileName()).isEqualTo("test.png");
        assertThat(fileId.getFileSystemName()).isEqualTo("test-1234.png");
        assertThat(fileId.getFileType()).isEqualTo(FileType.IMAGE);
        assertThat(fileId.getAuthority()).isEqualTo(Authority.PUBLIC_FILE_ACCESS);
        assertThat(fileId.getBaseDir()).isEqualTo("/test");
        assertThat(fileId.getDirectory()).isEqualTo("/test/public/images");
    }

}