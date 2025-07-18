package com.hoo.aar.adapter.in.web.user;

import com.hoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.admin.application.port.in.user.RegisterUserCommand;
import com.hoo.admin.application.port.in.user.RegisterUserUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostRegisterUserController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/aar/authn/regist")
    ResponseEntity<?> regist(@Valid @RequestBody Request request,
                             @NotNull @Jwt("snsId") Long snsId) {

        RegisterUserCommand command = new RegisterUserCommand(
                snsId,
                request.termsOfUseAgreement,
                request.personalInformationAgreement);

        return new ResponseEntity<>(registerUserUseCase.register(command), HttpStatus.CREATED);
    }

    private record Request(
            @NotNull Boolean termsOfUseAgreement,
            @NotNull Boolean personalInformationAgreement
    ) {
    }
}
