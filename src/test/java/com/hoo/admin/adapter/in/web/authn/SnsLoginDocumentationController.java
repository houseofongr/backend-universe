package com.hoo.admin.adapter.in.web.authn;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class SnsLoginDocumentationController {

    @GetMapping("/admin/authn/login/sns/admin-kakao")
    public void login(HttpServletResponse response) throws IOException {
        String redirectUrl = UriComponentsBuilder.fromUriString("https://admin.archiveofongr.site/login?sns-success")
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
