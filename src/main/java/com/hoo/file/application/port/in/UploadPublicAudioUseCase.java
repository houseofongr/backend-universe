package com.hoo.file.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadPublicAudioUseCase {
    UploadFileResult publicUpload(List<MultipartFile> audios);

    UploadFileResult.FileInfo publicUpload(MultipartFile audio);
}
