package com.hoo.file.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadPrivateImageUseCase {
    UploadFileResult privateUpload(List<MultipartFile> images);
}
