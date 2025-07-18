package com.hoo.admin.adapter.in.web.piece;

import com.hoo.admin.application.port.in.piece.CreatePieceCommand;
import com.hoo.admin.application.port.in.piece.CreatePieceResult;
import com.hoo.admin.application.port.in.piece.CreatePieceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostPieceController {

    private final CreatePieceUseCase useCase;

    @PostMapping("/admin/pieces/position")
    public ResponseEntity<CreatePieceResult> create(
            @RequestBody CreatePieceCommand command
    ) {
        return new ResponseEntity<>(useCase.create(command), HttpStatus.CREATED);
    }

}
