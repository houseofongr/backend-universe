package com.hoo.admin.adapter.in.web.room;

import com.hoo.admin.application.port.in.room.QueryRoomInfoUseCase;
import com.hoo.admin.application.port.in.room.QueryRoomResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetRoomController {

    private final QueryRoomInfoUseCase queryRoomInfoUseCase;

    @GetMapping("/admin/houses/rooms/{roomId}")
    ResponseEntity<QueryRoomResult> load(@PathVariable Long roomId) {
        return new ResponseEntity<>(queryRoomInfoUseCase.queryRoom(roomId), HttpStatus.OK);
    }

}
