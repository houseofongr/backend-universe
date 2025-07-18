package com.hoo.admin.adapter.in.web.room;

import com.hoo.admin.application.port.in.room.QueryRoomItemsResult;
import com.hoo.admin.application.port.in.room.QueryRoomItemsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetRoomItemsController {

    private final QueryRoomItemsUseCase queryRoomItemsUseCase;

    @GetMapping("/admin/homes/{homeId}/rooms/{roomId}/items")
    ResponseEntity<QueryRoomItemsResult> load(
            @PathVariable Long homeId,
            @PathVariable Long roomId) {
        return new ResponseEntity<>(queryRoomItemsUseCase.queryRoomItems(homeId, roomId), HttpStatus.OK);
    }
}
