package com.hoo.admin.adapter.in.web.authn;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class LoginDocumentationController {

    @PostMapping("/admin/authn/login")
    public void login(HttpServletResponse response) throws IOException {
        String redirectUrl = UriComponentsBuilder.fromUriString("https://admin.archiveofongr.site/login?form-success")
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
