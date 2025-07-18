package com.hoo.admin.adapter.in.web.user;

import com.hoo.admin.application.port.in.user.ConfirmBusinessUserResult;
import com.hoo.admin.application.port.in.user.ConfirmBusinessUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostConfirmBusinessUserController {

    private final ConfirmBusinessUserUseCase useCase;

    @PostMapping("/admin/users/business/confirm")
    ResponseEntity<ConfirmBusinessUserResult> confirm(
            @RequestParam Long tempUserId
    ) {
        return new ResponseEntity<>(useCase.confirm(tempUserId), HttpStatus.CREATED);
    }
}
