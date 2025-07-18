package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.domain.FileId;
import com.hoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicFileIdCreateStrategy implements FileIdCreateStrategy {

    private final String baseDir;
    private final Authority authority;
    private final FileType fileType;

    @Override
    public FileId create(String originalFilename, String fileSystemName) {
        return FileId.create(baseDir, authority, fileType, originalFilename, fileSystemName);
    }
}
