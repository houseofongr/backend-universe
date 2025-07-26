package com.hoo.universe.adapter.in.web;

import com.hoo.universe.api.in.dto.CreateUniverseCommand;
import com.hoo.universe.api.in.dto.CreateUniverseResult;
import com.hoo.universe.api.in.CreateUniverseUseCase;
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
    private final WebMapper webMapper;

    @PostMapping("/universes")
    public ResponseEntity<CreateUniverseResult> create(
            @RequestParam String metadata,
            @RequestParam("thumbmusic") MultipartFile thumbmusicFile,
            @RequestParam("thumbnail") MultipartFile thumbnailFile,
            @RequestParam("background") MultipartFile backgroundFile
    ) {

        CreateUniverseCommand command = new CreateUniverseCommand(
                webMapper.mapToCreateUniverseCommandMetadata(metadata),
                webMapper.mapToFileSource(thumbmusicFile),
                webMapper.mapToFileSource(thumbnailFile),
                webMapper.mapToFileSource(backgroundFile)
        );

        return new ResponseEntity<>(useCase.createNewUniverse(command), HttpStatus.CREATED);
    }
}
