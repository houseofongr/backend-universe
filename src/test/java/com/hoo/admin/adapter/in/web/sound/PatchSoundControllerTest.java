package com.hoo.admin.adapter.in.web.sound;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.SoundJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class PatchSoundControllerTest extends AbstractControllerTest {

    @Autowired
    SoundJpaRepository soundJpaRepository;

    @Test
    @DisplayName("소리 수정 API")
    void testUpdateSoundAPI() throws Exception {

        //language=JSON
        String content = """
                {
                  "title" : "수정할 제목을 입력합니다.",
                  "description" : "수정할 내용을 입력합니다.",
                  "hidden" : "false"
                }
                """;

        mockMvc.perform(patch("/admin/sounds/{soundId}", 1L)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("admin-sound-patch",
                        pathParameters(
                                parameterWithName("soundId").description("수정할 소리의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다.").optional(),
                                fieldWithPath("description").description("수정할 상세 설명입니다.").optional(),
                                fieldWithPath("hidden").description("수정할 숨김 여부입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : '[id]번 소리의 상세정보가 수정되었습니다.'"),
                                fieldWithPath("title").description("수정된 제목입니다."),
                                fieldWithPath("description").description("수정된 상세 설명입니다."),
                                fieldWithPath("hidden").description("수정된 숨김 여부입니다.")
                        )
                ));

        SoundJpaEntity soundJpaEntity = soundJpaRepository.findById(1L).orElseThrow();
        assertThat(soundJpaEntity.getTitle()).isEqualTo("수정할 제목을 입력합니다.");
        assertThat(soundJpaEntity.getDescription()).isEqualTo("수정할 내용을 입력합니다.");
    }
}