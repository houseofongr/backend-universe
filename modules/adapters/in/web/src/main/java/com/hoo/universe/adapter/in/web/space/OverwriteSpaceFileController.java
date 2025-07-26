package com.hoo.universe.adapter.in.web.space;

import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.universe.adapter.in.web.WebMapper;
import com.hoo.universe.api.in.dto.OverwriteSpaceFileResult;
import com.hoo.universe.api.in.OverwriteSpaceFileUseCase;
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
public class OverwriteSpaceFileController {

    private final OverwriteSpaceFileUseCase useCase;
    private final WebMapper webMapper;

    @PostMapping("/universes/{universeID}/spaces/{spaceID}/background")
    public ResponseEntity<OverwriteSpaceFileResult> overwriteSpaceFile(
            @PathVariable UUID universeID,
            @PathVariable UUID spaceID,
            @RequestPart(value = "background") MultipartFile backgroundFile) {

        UploadFileCommand.FileSource background = webMapper.mapToFileSource(backgroundFile);
        return ResponseEntity.ok(useCase.overwriteSpaceFile(universeID, spaceID, background));
    }
}
