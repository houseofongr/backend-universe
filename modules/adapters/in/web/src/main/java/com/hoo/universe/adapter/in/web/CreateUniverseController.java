package com.hoo.universe.adapter.in.web;

import com.hoo.universe.api.in.web.dto.command.CreateUniverseCommand;
import com.hoo.universe.api.in.web.dto.result.CreateUniverseResult;
import com.hoo.universe.api.in.web.usecase.CreateUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CreateUniverseController {

    private final CreateUniverseUseCase useCase;
    private final RequestMapper requestMapper;

    @PostMapping("/universes")
    public ResponseEntity<CreateUniverseResult> create(
            @RequestParam String metadata,
            @RequestParam("thumbmusic") MultipartFile thumbmusicFile,
            @RequestParam("thumbnail") MultipartFile thumbnailFile,
            @RequestParam("background") MultipartFile backgroundFile
    ) {

        CreateUniverseCommand command = new CreateUniverseCommand(
                requestMapper.mapToCreateUniverseCommandMetadata(metadata),
                requestMapper.mapToFileCommand(thumbmusicFile),
                requestMapper.mapToFileCommand(thumbnailFile),
                requestMapper.mapToFileCommand(backgroundFile)
        );

        return new ResponseEntity<>(useCase.createNewUniverse(command), HttpStatus.CREATED);
    }
}
