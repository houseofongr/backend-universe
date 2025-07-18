package com.hoo.admin.adapter.in.web.sound;

import com.hoo.admin.application.port.in.sound.DeleteSoundResult;
import com.hoo.admin.application.port.in.sound.DeleteSoundUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSoundController {

    private final DeleteSoundUseCase useCase;

    @DeleteMapping("/admin/sounds/{soundId}")
    ResponseEntity<DeleteSoundResult> delete(@PathVariable Long soundId) {
        return ResponseEntity.ok(useCase.delete(soundId));
    }
}
