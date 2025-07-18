package com.hoo.admin.adapter.in.web.house;

import com.hoo.admin.application.port.in.house.UpdateHouseInfoCommand;
import com.hoo.admin.application.port.in.house.UpdateHouseInfoUseCase;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchHouseController {

    private final UpdateHouseInfoUseCase updateHouseInfoUseCase;

    @PatchMapping("/admin/houses/{houseId}")
    ResponseEntity<MessageDto> update(
            @PathVariable Long houseId,
            @RequestBody Request request) {

        UpdateHouseInfoCommand command = new UpdateHouseInfoCommand(houseId,
                request.title(),
                request.author(),
                request.description());

        return new ResponseEntity<>(updateHouseInfoUseCase.update(command), HttpStatus.OK);
    }

    private record Request(
            String title,
            String author,
            String description
    ) {

    }
}
