package com.hoo.universe.adapter.in.web.piece;

import com.hoo.universe.adapter.in.web.RequestMapper;
import com.hoo.universe.api.dto.command.piece.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.dto.command.space.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.api.dto.result.piece.CreatePieceResult;
import com.hoo.universe.api.in.piece.CreatePieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CreatePieceController {

    private final CreatePieceUseCase useCase;
    private final RequestMapper requestMapper;

    @PostMapping("/universes/{universeID}/pieces")
    public ResponseEntity<CreatePieceResult> create(
            @PathVariable UUID universeID,
            @RequestParam String metadata,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {

        CreatePieceWithTwoPointCommand command = new CreatePieceWithTwoPointCommand(
                requestMapper.mapToCreatePieceCommandMetadata(metadata),
                requestMapper.mapToUploadFileRequest(imageFile));

        return new ResponseEntity<>(useCase.createNewPieceWithTwoPoint(universeID, command), HttpStatus.CREATED);
    }

}
