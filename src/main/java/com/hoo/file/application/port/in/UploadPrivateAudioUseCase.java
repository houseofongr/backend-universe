package com.hoo.file.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadPrivateAudioUseCase {
    UploadFileResult privateUpload(List<MultipartFile> audios, Long ownerId);
}
