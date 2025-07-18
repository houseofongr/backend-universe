package com.hoo.aar.adapter.in.web.user;

import com.hoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aar.application.port.in.user.DeleteMyInfoUseCase;
import com.hoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.common.application.port.in.MessageDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteMyAccountController {

    private final DeleteMyInfoUseCase useCase;

    @DeleteMapping("/aar/users")
    public ResponseEntity<MessageDto> deleteMyAccount(
            @NotNull @Jwt("userId") Long userId,
            @RequestBody DeleteUserCommand command
    ) {

        return ResponseEntity.ok(useCase.deleteMyInfo(userId, command));
    }
}
