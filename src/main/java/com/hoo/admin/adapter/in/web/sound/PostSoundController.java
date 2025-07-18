package com.hoo.admin.adapter.in.web.sound;

import com.hoo.admin.application.port.in.sound.CreateSoundCommand;
import com.hoo.admin.application.port.in.sound.CreateSoundResult;
import com.hoo.admin.application.port.in.sound.CreateSoundUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.hoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostSoundController {

    private final CreateSoundUseCase useCase;

    @PostMapping("/admin/sounds")
    ResponseEntity<CreateSoundResult> create(
            @RequestParam String metadata,
            @RequestParam("audio") MultipartFile audioFile
    ) {
        CreateSoundCommand baseCommand = gson.fromJson(metadata, CreateSoundCommand.class);
        CreateSoundCommand fullCommand = CreateSoundCommand.withAudioFile(baseCommand, audioFile);

        return new ResponseEntity<>(useCase.create(fullCommand), HttpStatus.CREATED);
    }
}
