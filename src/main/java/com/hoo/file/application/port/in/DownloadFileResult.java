package com.hoo.file.application.port.in;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;

public record DownloadFileResult(
        String disposition,
        MediaType mediaType,
        UrlResource resource
) {
}
