package com.hoo.universe.adapter.in.web.sound;

import com.hoo.universe.adapter.in.web.RequestMapper;
import com.hoo.universe.api.in.dto.CreateSoundCommand;
import com.hoo.universe.api.in.dto.CreateSoundResult;
import com.hoo.universe.api.in.CreateSoundUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CreateSoundController {

    private final CreateSoundUseCase useCase;
    private final RequestMapper requestMapper;

    @PostMapping("/universes/{universeID}/pieces/{parentPieceID}/sounds")
    ResponseEntity<CreateSoundResult> create(
            @PathVariable UUID universeID,
            @PathVariable UUID parentPieceID,
            @RequestParam String metadata,
            @RequestParam("audio") MultipartFile audioFile
    ) {

        CreateSoundCommand command = new CreateSoundCommand(
                requestMapper.mapToCreateSoundCommandMetadata(metadata),
                requestMapper.mapToFileSource(audioFile)
        );

        return new ResponseEntity<>(useCase.createNewSound(universeID, parentPieceID, command), HttpStatus.CREATED);
    }
}
