package com.hoo.universe.adapter.in.web.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.dto.command.space.CreateSpaceWithTwoPointCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateSpaceMetadataDocumentationController {

    @GetMapping("/universes/create-space-metadata")
    ResponseEntity<CreateSpaceWithTwoPointCommand.Metadata> getMetadata() {
        return ResponseEntity.ok(new CreateSpaceWithTwoPointCommand.Metadata(
                UuidCreator.getTimeOrderedEpoch(),
                "생성할 스페이스의 제목",
                "생성할 스페이스의 내용",
                0.1, 0.2, 0.3, 0.4,
                false
        ));
    }
}
