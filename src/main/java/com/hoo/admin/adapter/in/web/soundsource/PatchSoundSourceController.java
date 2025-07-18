package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.admin.application.port.in.soundsource.UpdateSoundSourceCommand;
import com.hoo.admin.application.port.in.soundsource.UpdateSoundSourceUseCase;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchSoundSourceController {

    private final UpdateSoundSourceUseCase updateSoundSourceUseCase;

    @PatchMapping("/admin/sound-sources/{soundSourceId}")
    ResponseEntity<MessageDto> updateSoundSource(
            @PathVariable Long soundSourceId,
            @RequestBody UpdateSoundSourceCommand command) {
        return ResponseEntity.ok(updateSoundSourceUseCase.updateSoundSource(soundSourceId, command));
    }
}
