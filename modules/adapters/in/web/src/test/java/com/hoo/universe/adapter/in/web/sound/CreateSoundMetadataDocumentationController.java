package com.hoo.universe.adapter.in.web.sound;

import com.hoo.universe.api.in.web.dto.command.CreateSoundCommand;
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
