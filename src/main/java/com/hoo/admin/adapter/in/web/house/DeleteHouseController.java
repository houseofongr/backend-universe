package com.hoo.admin.adapter.in.web.house;

import com.hoo.admin.application.port.in.house.DeleteHouseUseCase;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteHouseController {

    private final DeleteHouseUseCase deleteHouseUseCase;

    @DeleteMapping("/admin/houses/{houseId}")
    ResponseEntity<MessageDto> delete(
            @PathVariable Long houseId
    ) {
        return new ResponseEntity<>(deleteHouseUseCase.deleteHouse(houseId), HttpStatus.OK);
    }

}
