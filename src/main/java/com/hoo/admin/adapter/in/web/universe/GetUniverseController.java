package com.hoo.admin.adapter.in.web.universe;

import com.hoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.admin.application.port.in.universe.SearchUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUniverseController {

    private final SearchUniverseUseCase useCase;

    @GetMapping("/admin/universes/{universeId}")
    public ResponseEntity<SearchUniverseResult.UniverseDetailInfo> get(@PathVariable Long universeId) {

        return ResponseEntity.ok(useCase.search(universeId));
    }
}
