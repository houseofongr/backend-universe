package com.hoo.aar.adapter.in.web.home;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.ownerity.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetSoundSourceControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetSoundSourceControllerTest.sql")
    @DisplayName("음원 조회 API")
    void testQuerySoundSource() throws Exception {
        mockMvc.perform(get("/aar/homes/sound-sources")
                        .param("soundSourceId", "1")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-home-get-soundsource",
                        queryParameters(
                                parameterWithName("soundSourceId").description("조회할 음원의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("name").description("조회된 음원의 이름입니다."),
                                fieldWithPath("description").description("조회된 음원의 상세설명입니다."),
                                fieldWithPath("createdDate").description("조회된 음원의 생성일입니다."),
                                fieldWithPath("updatedDate").description("조회된 음원의 수정일입니다."),
                                fieldWithPath("audioFileId").description("해당 음원이 보유한 음악 파일의 ID입니다.")
                        )
                ));
    }
}