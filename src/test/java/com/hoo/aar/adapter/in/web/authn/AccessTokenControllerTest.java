package com.hoo.aar.adapter.in.web.authn;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccessTokenControllerTest extends AbstractControllerTest {


    @Test
    @DisplayName("Access Token Snippet 생성 테스트")
    void testCreateAccessTokenSnippet() throws Exception {
        mockMvc.perform(get("/access-token"))
                .andExpect(status().is(200))
                .andDo(document("aar-access-token",
                        responseFields(
                                fieldWithPath("accessToken").description("사용자의 정보가 담긴 JWT 액세스 토큰입니다. +" + "\n" + "* Claims : [userId, snsId, nickname, role] +" + "\n" + "* Header 형식 : [Bearer {accessToken}]")
                        )
                ));
    }
}
