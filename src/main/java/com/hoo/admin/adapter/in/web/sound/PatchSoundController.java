package com.hoo.admin.adapter.in.web.sound;

import com.hoo.admin.application.port.in.sound.UpdateSoundCommand;
import com.hoo.admin.application.port.in.sound.UpdateSoundResult;
import com.hoo.admin.application.port.in.sound.UpdateSoundUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchSoundController {

    private final UpdateSoundUseCase useCase;

    @PatchMapping("/admin/sounds/{soundId}")
    ResponseEntity<UpdateSoundResult.Detail> update(
            @PathVariable Long soundId,
            @RequestBody UpdateSoundCommand command) {
        return ResponseEntity.ok(useCase.updateDetail(soundId, command));
    }
}
