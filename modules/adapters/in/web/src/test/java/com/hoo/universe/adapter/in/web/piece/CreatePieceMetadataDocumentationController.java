package com.hoo.universe.adapter.in.web.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.dto.CreatePieceWithTwoPointCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatePieceMetadataDocumentationController {

    @GetMapping("/universes/create-piece-metadata")
    ResponseEntity<CreatePieceWithTwoPointCommand.Metadata> getMetadata() {
        return ResponseEntity.ok(new CreatePieceWithTwoPointCommand.Metadata(
                UuidCreator.getTimeOrderedEpoch(),
                "생성할 피스의 제목",
                "생성할 피스의 내용",
                0.1, 0.2, 0.3, 0.4,
                false
        ));
    }
}
