package com.hoo.file.application.service;

import com.hoo.file.domain.FileId;

public interface FileIdCreateStrategy {
    FileId create(String originalFilename, String fileSystemName);
}
