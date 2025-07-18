package com.hoo.admin.adapter.in.web.universe;

import com.hoo.admin.application.port.in.universe.DeleteUniverseResult;
import com.hoo.admin.application.port.in.universe.DeleteUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteUniverseController {

    private final DeleteUniverseUseCase useCase;

    @DeleteMapping("/admin/universes/{universeId}")
    public ResponseEntity<DeleteUniverseResult> delete(@PathVariable Long universeId) {
        return ResponseEntity.ok(useCase.delete(universeId));
    }
}
