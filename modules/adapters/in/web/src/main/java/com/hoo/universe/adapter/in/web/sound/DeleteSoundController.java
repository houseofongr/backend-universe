package com.hoo.universe.adapter.in.web.sound;

import com.hoo.universe.api.in.dto.DeleteSoundResult;
import com.hoo.universe.api.in.DeleteSoundUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeleteSoundController {

    private final DeleteSoundUseCase useCase;

    @DeleteMapping("/universes/{universeID}/pieces/{parentPieceID}/sounds/{soundID}")
    ResponseEntity<DeleteSoundResult> delete(
            @PathVariable UUID universeID,
            @PathVariable UUID parentPieceID,
            @PathVariable UUID soundID
    ) {
        return ResponseEntity.ok(useCase.deleteSound(universeID, parentPieceID, soundID));
    }
}
