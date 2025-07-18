package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.in.DownloadImageUseCase;
import com.hoo.file.application.port.in.DownloadPublicImageUseCase;
import com.hoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadImageService implements DownloadPublicImageUseCase, DownloadImageUseCase {

    private final DownloadService downloadService;

    @Override
    public DownloadFileResult publicDownload(Long fileId, boolean attachment) {
        return downloadService.load(fileId, FileType.IMAGE, Authority.PUBLIC_FILE_ACCESS, attachment);
    }

    @Override
    public DownloadFileResult privateDownload(Long fileId) {
        return downloadService.load(fileId, FileType.IMAGE, Authority.ALL_PRIVATE_IMAGE_ACCESS, false);
    }
}
