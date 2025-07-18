package com.hoo.admin.adapter.in.web.universe;

import com.hoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.hoo.admin.application.port.in.universe.UpdateUniverseResult;
import com.hoo.admin.application.port.in.universe.UpdateUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchUniverseController {

    private final UpdateUniverseUseCase useCase;

    @PatchMapping("/admin/universes/{universeId}")
    public ResponseEntity<UpdateUniverseResult.Detail> update(
            @PathVariable Long universeId,
            @RequestBody UpdateUniverseCommand command) {

        return ResponseEntity.ok(useCase.updateDetail(universeId, command));

    }

}
