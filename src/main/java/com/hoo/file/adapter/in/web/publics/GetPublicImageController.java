package com.hoo.file.adapter.in.web.publics;

import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.in.DownloadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPublicImageController {

    private final DownloadPublicImageUseCase downloadPublicImageUseCase;

    @GetMapping("/public/images/{fileId}")
    public ResponseEntity<?> download(
            @PathVariable Long fileId,
            @RequestParam(required = false) Boolean attachment
    ) {

        DownloadFileResult result = attachment != null && attachment?
                downloadPublicImageUseCase.publicDownload(fileId, true) :
                downloadPublicImageUseCase.publicDownload(fileId, false) ;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }
}
