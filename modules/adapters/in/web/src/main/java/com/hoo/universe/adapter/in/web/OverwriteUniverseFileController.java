package com.hoo.universe.adapter.in.web;

import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.universe.api.in.dto.OverwriteUniverseFileResult;
import com.hoo.universe.api.in.OverwriteUniverseFileUseCase;
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
public class OverwriteUniverseFileController {

    private final OverwriteUniverseFileUseCase useCase;
    private final RequestMapper requestMapper;

    @PostMapping("/universes/{universeID}/thumbmusic")
    public ResponseEntity<OverwriteUniverseFileResult.Thumbmusic> updateThumbMusic(
            @PathVariable UUID universeID,
            @RequestPart(value = "thumbmusic") MultipartFile thumbmusicFile) {

        UploadFileCommand.FileSource thumbmusic = requestMapper.mapToFileSource(thumbmusicFile);

        return ResponseEntity.ok(useCase.overwriteUniverseThumbmusic(universeID, thumbmusic));

    }

    @PostMapping("/universes/{universeID}/thumbnail")
    public ResponseEntity<OverwriteUniverseFileResult.Thumbnail> updateThumbnail(
            @PathVariable UUID universeID,
            @RequestPart(value = "thumbnail") MultipartFile thumbnailFile) {

        UploadFileCommand.FileSource thumbnail = requestMapper.mapToFileSource(thumbnailFile);

        return ResponseEntity.ok(useCase.overwriteUniverseThumbnail(universeID, thumbnail));
    }

    @PostMapping("/universes/{universeID}/background")
    public ResponseEntity<OverwriteUniverseFileResult.Background> updateBackground(
            @PathVariable UUID universeID,
            @RequestPart(value = "background") MultipartFile backgroundFile) {

        UploadFileCommand.FileSource background = requestMapper.mapToFileSource(backgroundFile);

        return ResponseEntity.ok(useCase.overwriteUniverseBackground(universeID, background));

    }

}
