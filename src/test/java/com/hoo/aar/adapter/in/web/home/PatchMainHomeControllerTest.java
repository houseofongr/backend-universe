package com.hoo.aar.adapter.in.web.home;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.ownerity.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchMainHomeControllerTest extends AbstractControllerTest {

    @Autowired
    HomeJpaRepository homeJpaRepository;

    @Test
    @Sql("PatchMainHomeControllerTest.sql")
    @DisplayName("메인 홈 변경 API")
    void testChangeMainHome() throws Exception {
        mockMvc.perform(patch("/aar/homes/{homeId}/main", 1L)
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-home-patch-main",
                        pathParameters(
                                parameterWithName("homeId").description("수정할 집의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 홈이 메인으로 수정되었습니다.")
                        )
                ));

        Assertions.assertThat(homeJpaRepository.findByUserIdAndIsMain(10L, true).orElseThrow().getId()).isEqualTo(1L);
        Assertions.assertThat(homeJpaRepository.findByUserIdAndIsMain(11L, true).orElseThrow().getId()).isEqualTo(7L);
    }

}