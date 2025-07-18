package com.hoo.file.adapter.out.persistence;

import com.hoo.common.domain.Authority;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.application.service.FileProperties;
import com.hoo.file.domain.File;
import com.hoo.file.domain.FileF;
import com.hoo.file.domain.FileStatus;
import com.hoo.file.domain.FileType;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.file.domain.exception.IllegalFileTypeDirException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({FilePersistenceAdapter.class, FileMapper.class, FileProperties.class})
class FilePersistenceAdapterTest {

    @Autowired
    FilePersistenceAdapter sut;

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @DisplayName("파일 엔티티 저장")
    void testSave() {
        // given
        File file = FileF.IMAGE_FILE_1.get("/tmp");

        // when
        Long id = sut.save(file);

        FileJpaEntity entityInDB = fileJpaRepository.findByFileSystemNameAndAbsolutePath(file.getFileId().getFileSystemName(), file.getFileId().getDirectory());

        // then
        assertThat(entityInDB.getId()).isEqualTo(id);
        assertThat(entityInDB.getIsDeleted()).isFalse();
        assertThat(entityInDB.getCreatedTime()).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
        assertThat(entityInDB.getUpdatedTime()).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    @Sql("FilePersistenceAdapterTest.sql")
    @DisplayName("이미지 파일 엔티티 조회")
    void testLoadPublicImageFile() throws FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {
        // given
        Long fileId = 1L;

        // when
        Optional<File> optional = sut.load(fileId);

        // then
        assertThat(optional).isNotEmpty();

        File file = optional.get();

        assertThat(file.getFileId().getBaseDir()).isEqualTo("/tmp");
        assertThat(file.getFileId().getRealFileName()).isEqualTo("test.png");
        assertThat(file.getSize().getFileByte()).isEqualTo(1234);
        assertThat(file.getOwnerId()).isNull();
        assertThat(file.getStatus()).isEqualTo(FileStatus.CREATED);
        assertThat(file.getFileId().getFileType()).isEqualTo(FileType.IMAGE);
        assertThat(file.getFileId().getAuthority()).isEqualTo(Authority.PUBLIC_FILE_ACCESS);
    }

    @Test
    @Sql("FilePersistenceAdapterTest2.sql")
    @DisplayName("오디오 파일 엔티티 조회")
    void testLoadAudioFile() throws FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {
        // given
        Long fileId = 1L;

        // when
        Optional<File> optional = sut.load(fileId);

        // then
        assertThat(optional).isNotEmpty();

        File file = optional.get();

        assertThat(file.getFileId().getBaseDir()).isEqualTo("/tmp");
        assertThat(file.getFileId().getRealFileName()).isEqualTo("test.mp3");
        assertThat(file.getSize().getFileByte()).isEqualTo(1234);
        assertThat(file.getOwnerId()).isNull();
        assertThat(file.getStatus()).isEqualTo(FileStatus.CREATED);
        assertThat(file.getFileId().getFileType()).isEqualTo(FileType.AUDIO);
        assertThat(file.getFileId().getAuthority()).isEqualTo(Authority.ALL_PRIVATE_AUDIO_ACCESS);
    }

    @Test
    @Sql("FilePersistenceAdapterTest3.sql")
    @DisplayName("파일 엔티티 전체조회")
    void testLoadAllFile() {
        // given
        List<Long> ids = List.of(1L, 2L, 3L, 4L, 5L);

        // when
        List<File> files = sut.loadAll(ids);

        // then
        assertThat(files).hasSize(5)
                .anyMatch(file -> file.getFileId().getRealFileName().equals("test.png"))
                .anyMatch(file -> file.getFileId().getRealFileName().equals("test2.png"))
                .anyMatch(file -> file.getFileId().getRealFileName().equals("test3.png"))
                .anyMatch(file -> file.getFileId().getRealFileName().equals("test4.png"))
                .anyMatch(file -> file.getFileId().getRealFileName().equals("test5.png"));
    }

    @Test
    @Sql("FilePersistenceAdapterTest2.sql")
    @DisplayName("파일 엔티티 삭제")
    void deleteEntity() {
        // given
        Long id = 1L;

        // when
        sut.delete(id);

        // then
        assertThat(fileJpaRepository.findById(id)).isEmpty();
    }

    @Test
    @Sql("FilePersistenceAdapterTest3.sql")
    @DisplayName("파일 엔티티 전체 삭제")
    void deleteAllEntities() {
        // given
        List<Long> ids = List.of(1L, 2L, 3L, 4L, 5L);

        // when
        sut.deleteAll(ids);

        // then
        assertThat(fileJpaRepository.findAllById(ids)).isEmpty();
    }
}