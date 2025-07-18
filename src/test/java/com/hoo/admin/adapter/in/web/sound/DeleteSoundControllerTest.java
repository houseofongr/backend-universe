package com.hoo.admin.adapter.in.web.sound;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.repository.SoundJpaRepository;
import com.hoo.file.domain.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class DeleteSoundControllerTest extends AbstractControllerTest {

    @Autowired
    SoundJpaRepository soundJpaRepository;

    @Test
    @Transactional
    @DisplayName("사운드 삭제 API")
    void testDeleteSound() throws Exception {

        saveFile(42L, FileType.AUDIO);

        mockMvc.perform(delete("/admin/sounds/{soundId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-sound-delete",
                        pathParameters(
                                parameterWithName("soundId").description("삭제할 사운드 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : '[#id]번 사운드가 삭제되었습니다.'"),
                                fieldWithPath("deletedSoundId").description("삭제된 사운드 ID입니다."),
                                fieldWithPath("deletedAudioId").description("삭제된 오디오 파일 ID입니다.")
                        )
                ));

        assertThat(soundJpaRepository.findAll()).noneMatch(soundJpaEntity -> soundJpaEntity.getId().equals(1L));
        assertThat(fileJpaRepository.findAll()).noneMatch(fileJpaEntity -> fileJpaEntity.getId().equals(42L));
    }
}