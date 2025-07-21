package com.hoo.universe.adapter.in.web.space;

import com.hoo.universe.api.in.dto.UpdateSpaceMetadataCommand;
import com.hoo.universe.api.in.dto.UpdateSpaceMetadataResult;
import com.hoo.universe.api.in.UpdateSpaceMetadataUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdateSpaceMetadataController {

    private final UpdateSpaceMetadataUseCase useCase;

    @PatchMapping("/universes/{universeID}/spaces/{spaceID}")
    public ResponseEntity<UpdateSpaceMetadataResult> update(
            @PathVariable UUID universeID,
            @PathVariable UUID spaceID,
            @RequestBody UpdateSpaceMetadataCommand command) {

        return ResponseEntity.ok(useCase.updateSpaceMetadata(universeID, spaceID, command));
    }
}
