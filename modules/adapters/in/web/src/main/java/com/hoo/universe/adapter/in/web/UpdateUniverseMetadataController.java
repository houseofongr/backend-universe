package com.hoo.universe.adapter.in.web;

import com.hoo.universe.api.dto.command.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.dto.result.UpdateUniverseMetadataResult;
import com.hoo.universe.api.in.UpdateUniverseMetadataUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdateUniverseMetadataController {

    private final UpdateUniverseMetadataUseCase useCase;

    @PatchMapping("/universes/{universeID}")
    public ResponseEntity<UpdateUniverseMetadataResult> update(
            @PathVariable UUID universeID,
            @RequestBody UpdateUniverseMetadataCommand command) {

        return ResponseEntity.ok(useCase.updateUniverseMetadata(universeID, command));

    }

}
