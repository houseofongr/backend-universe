package com.hoo.file.adapter.in.web.privates;

import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.in.DownloadPrivateAudioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPrivateAudioController {

    private final DownloadPrivateAudioUseCase downloadPrivateAudioUseCase;

    @GetMapping("/private/audios/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {

        DownloadFileResult result = downloadPrivateAudioUseCase.privateDownload(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }
}
