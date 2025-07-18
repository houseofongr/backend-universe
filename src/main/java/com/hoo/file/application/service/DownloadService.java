package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.out.database.FindFilePort;
import com.hoo.file.domain.File;
import com.hoo.file.domain.FileId;
import com.hoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadService {

    private final FindFilePort findFilePort;

    public DownloadFileResult load(Long fileId, FileType type, Authority authority, boolean attachment) {
        try {

            File loadedFile = findFilePort.load(fileId)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            FileId loadedFileId = loadedFile.getFileId();

            if (loadedFileId.getFileType() != type)
                throw new FileException(FileErrorCode.INVALID_FILE_TYPE);

            if (loadedFileId.getAuthority() != authority)
                throw new FileException(FileErrorCode.INVALID_AUTHORITY);

            ContentDisposition disposition = getDisposition(loadedFileId, attachment);

            return new DownloadFileResult(
                    disposition.toString(),
                    MediaType.parseMediaType(Files.probeContentType(Path.of(loadedFileId.getFilePath()))),
                    new UrlResource(loadedFileId.getFilePath()));

        } catch (IOException e) {

            throw new FileException(e, FileErrorCode.RETRIEVE_FILE_FAILED);

        }
    }

    private ContentDisposition getDisposition(FileId fileId, boolean isAttachment) {
        return isAttachment ?
                ContentDisposition.attachment().filename(
                        URLEncoder.encode(fileId.getRealFileName(), StandardCharsets.UTF_8))
                        .build() :
                ContentDisposition.inline().filename(
                        fileId.getFileSystemName())
                        .build();
    }
}
