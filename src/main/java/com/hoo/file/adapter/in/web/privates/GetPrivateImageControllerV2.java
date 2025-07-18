package com.hoo.file.adapter.in.web.privates;

import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.in.DownloadPrivateImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPrivateImageControllerV2 {

    private final DownloadPrivateImageUseCase downloadImageUseCase;

    @GetMapping("/private/images/v2/{fileId}")
    public ResponseEntity<UrlResource> download(
            @RequestParam String accessToken,
            @PathVariable Long fileId) {

        DownloadFileResult result = downloadImageUseCase.privateDownload(accessToken, fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }

}
