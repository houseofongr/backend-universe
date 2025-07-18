package com.hoo.admin.adapter.in.web.space;

import com.hoo.admin.application.port.in.space.DeleteSpaceResult;
import com.hoo.admin.application.port.in.space.DeleteSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSpaceController {

    private final DeleteSpaceUseCase useCase;

    @DeleteMapping("/admin/spaces/{spaceId}")
    ResponseEntity<DeleteSpaceResult> delete(@PathVariable Long spaceId) {
        return ResponseEntity.ok(useCase.delete(spaceId));
    }

}
