package com.hoo.universe.adapter.in.web.sound;

import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.universe.adapter.in.web.WebMapper;
import com.hoo.universe.api.in.dto.OverwriteSoundFileResult;
import com.hoo.universe.api.in.OverwriteSoundFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OverwriteSoundFileController {

    private final OverwriteSoundFileUseCase useCase;
    private final WebMapper webMapper;

    @PostMapping("/universes/{universeID}/pieces/{parentPieceID}/sound/{soundID}/audio")
    ResponseEntity<OverwriteSoundFileResult> update(
            @PathVariable UUID universeID,
            @PathVariable UUID parentPieceID,
            @PathVariable UUID soundID,
            @RequestPart(value = "audio") MultipartFile audioFile) {

        UploadFileCommand.FileSource audio = webMapper.mapToFileSource(audioFile);
        return ResponseEntity.ok(useCase.overwriteSoundAudio(universeID, parentPieceID, soundID, audio));
    }
}
