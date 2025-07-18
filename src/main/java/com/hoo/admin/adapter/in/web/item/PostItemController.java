package com.hoo.admin.adapter.in.web.item;

import com.hoo.admin.application.port.in.item.CreateItemCommand;
import com.hoo.admin.application.port.in.item.CreateItemResult;
import com.hoo.admin.application.port.in.item.CreateItemUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class PostItemController {

    private final CreateItemUseCase createItemUseCase;

    @PostMapping("/admin/users/{userId}/homes/{homeId}/rooms/{roomId}/items")
    public ResponseEntity<CreateItemResult> createItem(
            @PathVariable Long userId,
            @PathVariable Long homeId,
            @PathVariable Long roomId,
            @Valid @RequestBody CreateItemCommand command) {

        return new ResponseEntity<>(createItemUseCase.create(homeId, roomId, userId, command), CREATED);

    }
}
