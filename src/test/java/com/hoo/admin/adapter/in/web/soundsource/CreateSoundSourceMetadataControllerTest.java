package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class CreateSoundSourceMetadataControllerTest extends AbstractControllerTest {


    @Test
    @DisplayName("음원 메타데이터 생성 테스트")
    void testCreateSoundSourceMetadata() throws Exception {
        mockMvc.perform(get("/mock/admin/create-soundsource-metadata")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("admin-create-soundsource-metadata",
                        responseFields(
                                fieldWithPath("name").description("생성할 음원의 이름입니다."),
                                fieldWithPath("description").description("생성할 음원의 상세 설명입니다."),
                                fieldWithPath("isActive").description("생성할 음원을 활성화할지 여부입니다. +" + "\n" + "* 기본값 : 활성화")
                        )
                ));
    }
}