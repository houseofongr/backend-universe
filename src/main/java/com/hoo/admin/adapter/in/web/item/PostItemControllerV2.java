package com.hoo.admin.adapter.in.web.item;

import com.hoo.admin.application.port.in.item.CreateAndUpdateItemCommand;
import com.hoo.admin.application.port.in.item.CreateAndUpdateItemResult;
import com.hoo.admin.application.port.in.item.CreateAndUpdateItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class PostItemControllerV2 {

    private final CreateAndUpdateItemUseCase createAndUpdateItemUseCase;

    @PostMapping("/admin/users/{userId}/homes/{homeId}/rooms/{roomId}/items/v2")
    public ResponseEntity<CreateAndUpdateItemResult> createItem(
            @PathVariable Long userId,
            @PathVariable Long homeId,
            @PathVariable Long roomId,
            @RequestBody CreateAndUpdateItemCommand command) {

        return new ResponseEntity<>(createAndUpdateItemUseCase.createAndUpdate(homeId, roomId, userId, command), CREATED);

    }
}
