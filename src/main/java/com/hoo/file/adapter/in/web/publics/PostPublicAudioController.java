package com.hoo.file.adapter.in.web.publics;

import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicAudioUseCase;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostPublicAudioController {

    private final UploadPublicAudioUseCase uploadPublicAudioUseCase;

    @PostMapping("/public/audios")
    public ResponseEntity<UploadFileResult> upload(@NotEmpty @RequestParam List<MultipartFile> audios) {
        UploadFileResult response = uploadPublicAudioUseCase.publicUpload(audios);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
