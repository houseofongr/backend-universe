package com.hoo.universe.adapter.in.web.space;

import com.hoo.universe.api.dto.command.space.MoveSpaceWithTwoPointCommand;
import com.hoo.universe.api.dto.result.space.MoveSpaceWithTwoPointResult;
import com.hoo.universe.api.in.space.MoveSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MoveSpaceController {

    private final MoveSpaceUseCase useCase;

    @PatchMapping("/universes/{universeID}/spaces/{spaceID}/move")
    ResponseEntity<MoveSpaceWithTwoPointResult> move(
            @PathVariable UUID universeID,
            @PathVariable UUID spaceID,
            @RequestBody MoveSpaceWithTwoPointCommand command
    ) {

        return ResponseEntity.ok(useCase.moveSpaceWithTwoPoint(universeID, spaceID, command));
    }
}
