package com.hoo.universe.adapter.in.web;

import com.hoo.universe.api.dto.result.DeleteUniverseResult;
import com.hoo.universe.api.in.DeleteUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeleteUniverseController {

    private final DeleteUniverseUseCase useCase;

    @DeleteMapping("/universes/{universeID}")
    public ResponseEntity<DeleteUniverseResult> delete(@PathVariable UUID universeID) {

        return ResponseEntity.ok(useCase.deleteUniverse(universeID));
    }
}
