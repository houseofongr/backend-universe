package com.hoo.admin.adapter.in.web.resource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RefreshController {

    @GetMapping({"/login", "/users/**", "/houses/**", "/sound-sources/**"})
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getServerName().contains("admin") || request.getServerName().contains("localhost"))
            request.getRequestDispatcher("/index.html").forward(request, response);

        else response.sendError(HttpServletResponse.SC_NOT_FOUND);

    }
}
