package com.hoo.admin.adapter.in.web.item;

import com.hoo.admin.application.port.in.item.UpdateItemCommand;
import com.hoo.admin.application.port.in.item.UpdateItemUseCase;
import com.hoo.common.application.port.in.MessageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchItemController {

    private final UpdateItemUseCase updateItemUseCase;

    @PatchMapping("/admin/items/{itemId}")
    public ResponseEntity<MessageDto> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateItemCommand command
    ) {
        return ResponseEntity.ok(updateItemUseCase.updateItem(itemId, command));
    }
}
