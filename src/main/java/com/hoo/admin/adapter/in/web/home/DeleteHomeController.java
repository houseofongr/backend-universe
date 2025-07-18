package com.hoo.admin.adapter.in.web.home;

import com.hoo.admin.application.port.in.home.DeleteHomeUseCase;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteHomeController {

    private final DeleteHomeUseCase deleteHomeUseCase;

    @DeleteMapping("/admin/homes/{homeId}")
    public ResponseEntity<MessageDto> delete(@PathVariable Long homeId) {
        return new ResponseEntity<>(deleteHomeUseCase.deleteHome(homeId), HttpStatus.OK);
    }

}
