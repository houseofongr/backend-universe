package com.hoo.admin.adapter.in.web.space;

import com.hoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.hoo.admin.application.port.in.space.UpdateSpaceResult;
import com.hoo.admin.application.port.in.space.UpdateSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchSpaceController {

    private final UpdateSpaceUseCase useCase;

    @PatchMapping("/admin/spaces/{spaceId}")
    public ResponseEntity<UpdateSpaceResult.Detail> update(
            @PathVariable Long spaceId,
            @RequestBody UpdateSpaceCommand.Detail command) {

        return ResponseEntity.ok(useCase.updateDetail(spaceId, command));
    }

    @PatchMapping("/admin/spaces/position/{spaceId}")
    public ResponseEntity<UpdateSpaceResult.Position> updatePosition(
            @PathVariable Long spaceId,
            @RequestBody UpdateSpaceCommand.Position command) {

        return ResponseEntity.ok(useCase.updatePosition(spaceId, command));
    }
}
