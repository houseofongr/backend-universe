package com.hoo.aar.adapter.in.web.home;

import com.hoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aar.application.port.in.home.UpdateHomeNameCommand;
import com.hoo.aar.application.port.in.home.UpdateHomeNameUseCase;
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
public class PatchHomeNameController {

    private final UpdateHomeNameUseCase useCase;

    @PatchMapping("/aar/homes/{homeId}")
    public ResponseEntity<MessageDto> patchHomeName(@Jwt("userId") Long userId,
                                                    @PathVariable Long homeId,
                                                    @Valid @RequestBody UpdateHomeNameCommand command) {
        return ResponseEntity.ok(useCase.updateHomeName(userId, homeId, command.newName()));
    }
}
