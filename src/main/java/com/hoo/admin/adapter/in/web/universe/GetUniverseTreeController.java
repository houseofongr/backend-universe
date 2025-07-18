package com.hoo.admin.adapter.in.web.universe;

import com.hoo.admin.application.port.in.universe.TraversalUniverseResult;
import com.hoo.admin.application.port.in.universe.TraversalUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUniverseTreeController {

    private final TraversalUniverseUseCase useCase;

    @GetMapping("/admin/universes/tree/{universeId}")
    ResponseEntity<TraversalUniverseResult> getTree(@PathVariable Long universeId) {
        return ResponseEntity.ok(useCase.traversal(universeId));
    }
}
