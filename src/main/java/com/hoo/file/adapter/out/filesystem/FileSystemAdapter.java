package com.hoo.file.adapter.out.filesystem;

import com.hoo.file.application.port.out.filesystem.EraseFilePort;
import com.hoo.file.application.port.out.filesystem.RandomFileNamePort;
import com.hoo.file.application.port.out.filesystem.WriteFilePort;
import com.hoo.file.application.service.FileErrorCode;
import com.hoo.file.application.service.FileException;
import com.hoo.file.domain.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Component
public class FileSystemAdapter implements WriteFilePort, EraseFilePort, RandomFileNamePort {

    @Override
    public void write(File file, MultipartFile multipartFile) throws IOException {

        java.io.File javaFile = new java.io.File(file.getFileId().getPath());

        javaFile.getParentFile().mkdirs();

        if (!javaFile.createNewFile())
            throw new FileException(FileErrorCode.FILE_NAME_DUPLICATION);

        multipartFile.transferTo(javaFile);
    }

    @Override
    public String getName(String originalFileName) {

        String[] split = originalFileName.split("\\.");

        if (split.length < 2)
            throw new FileException(FileErrorCode.INVALID_FILE_EXTENSION);

        return UUID.randomUUID() + "." + split[split.length - 1];
    }

    @Override
    public void erase(File file) throws IOException {
        log.info("Try to delete file : {}", file.getFileId().getPath());
        Files.delete(Path.of(file.getFileId().getPath()));
    }
}
