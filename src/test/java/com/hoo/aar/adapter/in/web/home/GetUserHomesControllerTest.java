package com.hoo.aar.adapter.in.web.home;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetUserHomesControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetUserHomesControllerTest.sql")
    @DisplayName("사용자 홈 리스트 조회 API")
    void getUserHomesAPI() throws Exception {
        mockMvc.perform(get("/aar/homes")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-home-get-list",
                        responseFields(
                                fieldWithPath("homes[].id").description("홈의 ID입니다."),
                                fieldWithPath("homes[].basicImageId").description("홈의 기본 이미지 ID입니다."),
                                fieldWithPath("homes[].name").description("홈의 이름입니다."),
                                fieldWithPath("homes[].isMain").description("기본 홈인지 여부입니다.")
                        )
                ));
    }
}