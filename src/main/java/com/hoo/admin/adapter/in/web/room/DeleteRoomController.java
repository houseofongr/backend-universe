package com.hoo.admin.adapter.in.web.room;

import com.hoo.admin.application.port.in.room.DeleteRoomUseCase;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteRoomController {

    private final DeleteRoomUseCase deleteRoomUseCase;

    @DeleteMapping("/admin/houses/rooms/{roomId}")
    ResponseEntity<MessageDto> delete(@PathVariable Long roomId) {

        return new ResponseEntity<>(deleteRoomUseCase.deleteRoom(roomId), HttpStatus.OK);
    }
}
