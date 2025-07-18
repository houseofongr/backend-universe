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

class GetItemSoundSourcesControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetItemSoundSourcesControllerTest.sql")
    @DisplayName("아이템 음원 조회 API")
    void testGetItem() throws Exception {
        mockMvc.perform(get("/aar/homes/items/sound-sources")
                        .param("itemId", "1")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-home-get-item-soundsources",
                        queryParameters(
                                parameterWithName("itemId").description("조회할 아이템의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("itemName").description("아이템의 이름입니다."),

                                fieldWithPath("soundSources[].id").description("음원의 ID입니다."),
                                fieldWithPath("soundSources[].name").description("음원의 이름입니다."),
                                fieldWithPath("soundSources[].description").description("음원에 대한 설명입니다."),
                                fieldWithPath("soundSources[].createdDate").description("음원의 생성일입니다."),
                                fieldWithPath("soundSources[].updatedDate").description("음원의 최종 수정일입니다.")
                        )
                ));
    }

}