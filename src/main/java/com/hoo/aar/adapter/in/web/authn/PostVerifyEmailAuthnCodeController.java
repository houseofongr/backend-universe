package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.port.in.authn.VerifyEmailAuthnCodeResult;
import com.hoo.aar.application.port.in.authn.VerifyEmailAuthnCodeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostVerifyEmailAuthnCodeController {

    private final VerifyEmailAuthnCodeUseCase useCase;

    @PostMapping("/aar/authn/email-code/verify")
    ResponseEntity<VerifyEmailAuthnCodeResult> sendMail(
            @RequestParam String email,
            @RequestParam String code) {
        return ResponseEntity.ok(useCase.verify(email, code));
    }
}
