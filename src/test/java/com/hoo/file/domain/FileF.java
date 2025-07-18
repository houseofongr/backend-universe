package com.hoo.file.domain;

import com.hoo.common.domain.Authority;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileF {
    IMAGE_FILE_1(FileType.IMAGE, "test.png", "test-1234.png", 1234L),
    IMAGE_FILE_2(FileType.IMAGE, "test2.png", "test-1235.png", 1234L),
    IMAGE_FILE_3(FileType.IMAGE, "test3.png", "test-1236.png", 1234L),
    IMAGE_FILE_4(FileType.IMAGE, "test4.png", "test-1237.png", 1234L),
    AUDIO_FILE_1(FileType.AUDIO, "test.mp3", "test-2345.mp3", 1234L);

    private final FileType fileType;
    private final String realFileName;
    private final String fileSystemName;
    private final Long size;

    public File get(String baseDir) {
        switch (fileType) {
            case IMAGE -> {
                try {
                    return File.create(FileId.create(baseDir, Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE, realFileName, fileSystemName), FileStatus.CREATED, OwnerId.empty(), new FileSize(size, 100 * 1024 * 1024L));
                } catch (FileSizeLimitExceedException | FileExtensionMismatchException e) {
                    throw new RuntimeException(e);
                }
            }
            case AUDIO -> {
                try {
                    return File.create(FileId.create(baseDir, Authority.PUBLIC_FILE_ACCESS, FileType.AUDIO, realFileName, fileSystemName), FileStatus.CREATED, OwnerId.empty(), new FileSize(size, 100 * 1024 * 1024L));
                } catch (FileSizeLimitExceedException | FileExtensionMismatchException e) {
                    throw new RuntimeException(e);
                }
            }
            case VIDEO -> {
                throw new UnsupportedOperationException();
            }
        }
        throw new UnsupportedOperationException();
    }
}
