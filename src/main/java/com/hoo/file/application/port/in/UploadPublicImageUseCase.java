package com.hoo.file.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadPublicImageUseCase {
    UploadFileResult publicUpload(List<MultipartFile> images);

    UploadFileResult.FileInfo publicUpload(MultipartFile image);
}
