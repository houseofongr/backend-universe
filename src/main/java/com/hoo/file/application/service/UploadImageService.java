package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UploadImageService implements UploadPublicImageUseCase, UploadPrivateImageUseCase {

    private final FileProperties fileProperties;
    private final UploadService uploadService;

    @Override
    @Transactional
    public UploadFileResult publicUpload(List<MultipartFile> images) {
        return uploadService.upload(images, new BasicFileIdCreateStrategy(fileProperties.getBaseDir(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    public UploadFileResult.FileInfo publicUpload(MultipartFile image) {
        return uploadService.upload(image, new BasicFileIdCreateStrategy(fileProperties.getBaseDir(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    @Transactional
    public UploadFileResult privateUpload(List<MultipartFile> images) {
        return uploadService.upload(images, new BasicFileIdCreateStrategy(fileProperties.getBaseDir(), Authority.ALL_PRIVATE_IMAGE_ACCESS, FileType.IMAGE));
    }

}