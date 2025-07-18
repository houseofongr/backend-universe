package com.hoo.file.application.port.in;

public interface DownloadPublicAudioUseCase {
    DownloadFileResult publicDownload(Long fileId, boolean attachment);
}
