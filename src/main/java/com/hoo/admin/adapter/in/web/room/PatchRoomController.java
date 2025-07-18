package com.hoo.admin.adapter.in.web.room;

import com.hoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.admin.application.port.in.room.UpdateRoomInfoUseCase;
import com.hoo.common.application.port.in.MessageDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatchRoomController {

    private final UpdateRoomInfoUseCase updateRoomInfoUseCase;

    @PatchMapping("/admin/houses/rooms")
    ResponseEntity<MessageDto> update(@NotEmpty @RequestBody List<UpdateRoomInfoCommand.RoomInfo> requests) {

        return new ResponseEntity<>(updateRoomInfoUseCase.update(new UpdateRoomInfoCommand(requests)), HttpStatus.OK);
    }

}
