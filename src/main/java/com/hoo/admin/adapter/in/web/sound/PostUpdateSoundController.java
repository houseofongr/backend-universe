package com.hoo.admin.adapter.in.web.sound;

import com.hoo.admin.application.port.in.sound.UpdateSoundResult;
import com.hoo.admin.application.port.in.sound.UpdateSoundUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostUpdateSoundController {

    private final UpdateSoundUseCase useCase;

    @PostMapping("/admin/sounds/audio/{soundId}")
    ResponseEntity<UpdateSoundResult.Audio> update(
            @PathVariable Long soundId,
            @RequestPart(value = "audio") MultipartFile audio) {
        return ResponseEntity.ok(useCase.updateAudio(soundId, audio));
    }
}
