package com.hoo.aar.adapter.in.web.user;

import com.hoo.common.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchMyInfoControllerTest extends AbstractControllerTest {


    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    @Sql("PatchMyInfoControllerTest.sql")
    @DisplayName("본인정보 수정 API 테스트")
    void testPatchMyInfoAPI() throws Exception {

        //language=JSON
        String content = """
                {
                  "nickname" : "leaf2"
                }
                """;
        mockMvc.perform(patch("/aar/users/me")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-user-patch-me",
                        requestFields(
                                fieldWithPath("nickname").description("수정할 닉네임입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 본인정보가 수정되었습니다.")
                        )
                ));

        assertThat(userJpaRepository.findById(10L).get().getNickname()).isEqualTo("leaf2");
    }
}