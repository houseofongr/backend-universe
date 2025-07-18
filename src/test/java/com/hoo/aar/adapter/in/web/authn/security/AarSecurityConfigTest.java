package com.hoo.aar.adapter.in.web.authn.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AarSecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("전체 공개 URL 테스트")
    void testPublicURL() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}", "kakao").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302));
        mockMvc.perform(get("/aar/authn/login/kakao/callback", "kakao").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
    }

    @Test
    @DisplayName("등록되지 않은 URL 테스트")
    void testUnregisteredURL() throws Exception {
        mockMvc.perform(get("/aar/invalid_url").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    @DisplayName("등록되지 않은 URL 테스트")
    void test403() throws Exception {
        mockMvc.perform(get("/aar/invalid_url").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

}