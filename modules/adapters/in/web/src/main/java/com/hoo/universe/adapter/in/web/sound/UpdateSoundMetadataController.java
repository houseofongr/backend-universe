package com.hoo.universe.adapter.in.web.sound;

import com.hoo.universe.api.dto.command.sound.UpdateSoundMetadataCommand;
import com.hoo.universe.api.dto.result.sound.UpdateSoundMetadataResult;
import com.hoo.universe.api.in.sound.UpdateSoundMetadataUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdateSoundMetadataController {

    private final UpdateSoundMetadataUseCase useCase;

    @PatchMapping("/universes/{universeID}/pieces/{parentPieceID}/sound/{soundID}")
    ResponseEntity<UpdateSoundMetadataResult> update(
            @PathVariable UUID universeID,
            @PathVariable UUID parentPieceID,
            @PathVariable UUID soundID,
            @RequestBody UpdateSoundMetadataCommand command) {

        return ResponseEntity.ok(useCase.updateSoundMetadata(universeID, parentPieceID, soundID, command));
    }
}
