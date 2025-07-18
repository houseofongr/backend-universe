package com.hoo.aar.adapter.in.web.home;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.ownerity.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PatchHomeNameControllerTest extends AbstractControllerTest {

    @Autowired
    HomeJpaRepository homeJpaRepository;

    @Test
    @Sql("PatchHomeNameControllerTest.sql")
    @DisplayName("집 이름 수정 API")
    void testPatchHomeNameAPI() throws Exception {

        // 글자수 없을때(공백일때) 확인
        mockMvc.perform(patch("/aar/homes/{homeId}", 1L)
                        .content("{\"newName\": \" \"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                )
                .andExpect(status().is(400));

        // 글자수 50자 확인
        mockMvc.perform(patch("/aar/homes/{homeId}", 1L)
                        .content("{\"newName\": \"fiftylengthhousefiftylengthhousefiftylengthhousefif\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                )
                .andExpect(status().is(400));

        //language=JSON
        String request = """
                {
                  "newName": "new home name"
                }
                """;

        mockMvc.perform(patch("/aar/homes/{homeId}", 1L)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                )
                .andExpect(status().is(200))
                .andDo(document("aar-home-patch-name",
                        pathParameters(
                                parameterWithName("homeId").description("수정할 집의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("newName").description("수정할 새로운 이름입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 홈의 이름이 수정되었습니다.")
                        )
                ));

        assertThat(homeJpaRepository.findById(1L).orElseThrow().getName()).isEqualTo("new home name");
    }

}