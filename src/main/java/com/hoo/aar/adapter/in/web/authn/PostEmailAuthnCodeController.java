package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.port.in.authn.CreateEmailAuthnCodeResult;
import com.hoo.aar.application.port.in.authn.CreateEmailAuthnCodeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostEmailAuthnCodeController {

    private final CreateEmailAuthnCodeUseCase useCase;

    @PostMapping("/aar/authn/email-code")
    ResponseEntity<CreateEmailAuthnCodeResult> sendMail(@RequestParam String email) {
        return new ResponseEntity<>(useCase.create(email), HttpStatus.CREATED);
    }
}
