package com.hoo.universe.adapter.in.web.space;

import com.hoo.universe.api.in.web.dto.result.DeleteSpaceResult;
import com.hoo.universe.api.in.web.usecase.DeleteSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeleteSpaceController {

    private final DeleteSpaceUseCase useCase;

    @DeleteMapping("/universes/{universeID}/spaces/{spaceID}")
    ResponseEntity<DeleteSpaceResult> delete(
            @PathVariable UUID universeID,
            @PathVariable UUID spaceID) {

        return ResponseEntity.ok(useCase.deleteSpace(universeID, spaceID));
    }

}
