package com.hoo.aar.adapter.in.web.home;

import com.hoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aar.application.port.in.home.QuerySoundSourceResult;
import com.hoo.aar.application.port.in.home.QuerySoundSourceUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("AARGetSoundSourceController")
@RequiredArgsConstructor
public class GetSoundSourceController {

    private final QuerySoundSourceUseCase useCase;

    @GetMapping("/aar/homes/sound-sources")
    public ResponseEntity<QuerySoundSourceResult> getSoundSource(
            @NotNull @Jwt("userId") Long userId,
            @RequestParam Long soundSourceId
    ) {
        return ResponseEntity.ok(useCase.querySoundSource(userId, soundSourceId));
    }
}
