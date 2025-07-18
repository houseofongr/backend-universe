package com.hoo.admin.adapter.in.web.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RefreshController.class)
@AutoConfigureMockMvc(addFilters = false)
class RefreshControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("새로고침 테스트")
    void testRefresh() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().is(200)).andExpect(forwardedUrl("/index.html"));
        mockMvc.perform(get("/users")).andExpect(status().is(200)).andExpect(forwardedUrl("/index.html"));
        mockMvc.perform(get("/users/123")).andExpect(status().is(200)).andExpect(forwardedUrl("/index.html"));
        mockMvc.perform(get("/houses")).andExpect(status().is(200)).andExpect(forwardedUrl("/index.html"));
        mockMvc.perform(get("/houses/123")).andExpect(status().is(200)).andExpect(forwardedUrl("/index.html"));
        mockMvc.perform(get("/sound-sources")).andExpect(status().is(200)).andExpect(forwardedUrl("/index.html"));
        mockMvc.perform(get("/sound-sources/123")).andExpect(status().is(200)).andExpect(forwardedUrl("/index.html"));
    }

}