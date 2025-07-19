package com.hoo.universe.adapter.in.web;

import com.hoo.universe.api.in.web.dto.result.OpenUniverseResult;
import com.hoo.universe.api.in.web.usecase.OpenUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OpenUniverseController {

    private final OpenUniverseUseCase useCase;

    @GetMapping("/universes/{universeID}")
    public ResponseEntity<OpenUniverseResult> get(@PathVariable UUID universeID) {

        return ResponseEntity.ok(useCase.openUniverseWithComponents(universeID));
    }
}
