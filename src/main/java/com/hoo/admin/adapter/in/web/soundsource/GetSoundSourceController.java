package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceResult;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSoundSourceController {

    private final QuerySoundSourceUseCase querySoundSourceUseCase;

    @GetMapping("/admin/sound-sources/{soundSourceId}")
    public ResponseEntity<QuerySoundSourceResult> query(@PathVariable Long soundSourceId) {
        return ResponseEntity.ok(querySoundSourceUseCase.querySoundSource(soundSourceId));
    }
}
