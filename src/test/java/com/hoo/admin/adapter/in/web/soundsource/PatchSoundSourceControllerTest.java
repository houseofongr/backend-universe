package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchSoundSourceControllerTest extends AbstractControllerTest {


    @Test
    @Sql("PatchSoundSourceControllerTest.sql")
    @DisplayName("음원 수정 API")
    void testUpdateSoundSourceAPI() throws Exception {

        //language=JSON
        String content = """
                {
                  "name" : "골골골송",
                  "description" : "2026년 설이가 보내는 골골골송",
                  "isActive" : false
                }
                """;

        mockMvc.perform(patch("/admin/sound-sources/{soundSourceId}", 1L)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("admin-soundsource-patch",
                        pathParameters(
                                parameterWithName("soundSourceId").description("수정할 음원의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("name").description("음원의 변경할 이름입니다."),
                                fieldWithPath("description").description("음원의 변경할 상세 설명입니다."),
                                fieldWithPath("isActive").description("음원의 활성화 여부입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 음원의 정보가 수정되었습니다.")
                        )
                ));
    }
}