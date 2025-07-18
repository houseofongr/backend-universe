package com.hoo.file.application.service;

import com.hoo.file.application.port.out.database.DeleteFilePort;
import com.hoo.file.application.port.out.database.FindFilePort;
import com.hoo.file.application.port.out.filesystem.EraseFilePort;
import com.hoo.file.domain.File;
import com.hoo.file.domain.FileF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class DeleteFileServiceTest {

    DeleteFileService sut;

    FindFilePort findFilePort;
    DeleteFilePort deleteFilePort;
    EraseFilePort eraseFilePort;

    @BeforeEach
    void init() {
        findFilePort = mock();
        deleteFilePort = mock();
        eraseFilePort = mock();
        sut = new DeleteFileService(findFilePort, deleteFilePort, eraseFilePort);
    }

    @Test
    @DisplayName("파일 삭제 서비스 테스트")
    void testDeleteFile(@TempDir Path tempDir) throws IOException {
        // given
        Long id = 1L;
        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());

        // when
        when(findFilePort.load(id)).thenReturn(Optional.of(file));
        sut.deleteFile(id);

        // then
        verify(eraseFilePort, times(1)).erase(file);
        verify(deleteFilePort, times(1)).delete(id);
    }

    @Test
    @DisplayName("파일 전체 삭제 서비스 테스트")
    void testDeleteAllFile(@TempDir Path tempDir) throws IOException {
        // given
        List<Long> ids = List.of(1L, 2L, 3L, 4L, 5L);
        List<File> files = List.of(
                FileF.IMAGE_FILE_1.get(tempDir.toString()),
                FileF.IMAGE_FILE_1.get(tempDir.toString()),
                FileF.IMAGE_FILE_1.get(tempDir.toString()),
                FileF.IMAGE_FILE_1.get(tempDir.toString()),
                FileF.IMAGE_FILE_1.get(tempDir.toString())
        );

        // when
        when(findFilePort.loadAll(ids)).thenReturn(files);
        sut.deleteFiles(ids);

        // then
        verify(eraseFilePort, times(5)).erase(any());
        verify(deleteFilePort, times(1)).deleteAll(ids);
    }
}