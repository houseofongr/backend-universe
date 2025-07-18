package com.hoo.admin.adapter.in.web.item;

import com.hoo.admin.application.port.in.item.QueryItemResult;
import com.hoo.admin.application.port.in.item.QueryItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetItemController {

    private final QueryItemUseCase queryItemUseCase;

    @GetMapping("/admin/items/{itemId}/sound-sources")
    public ResponseEntity<QueryItemResult> getItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(queryItemUseCase.queryItem(itemId));
    }
}
