package com.hoo.universe.adapter.in.web.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.dto.command.piece.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.dto.command.sound.CreateSoundCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateSoundMetadataDocumentationController {

    @GetMapping("/universes/create-sound-metadata")
    ResponseEntity<CreateSoundCommand.Metadata> getMetadata() {
        return ResponseEntity.ok(new CreateSoundCommand.Metadata(
                "생성할 사운드의 제목",
                "생성할 사운드의 내용",
                false
        ));
    }
}
