package com.hoo.admin.adapter.in.web.sound;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.repository.SoundJpaRepository;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.domain.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class PostUpdateSoundControllerTest extends AbstractControllerTest {

    @Autowired
    SoundJpaRepository soundJpaRepository;

    @Test
    @Transactional
    @DisplayName("사운드 오디오파일 수정 API")
    void testUpdateSpaceInnerImageAPI() throws Exception {

        for (long i = 42; i <= 52; i++) saveFile(i, FileType.AUDIO);

        MockMultipartFile audio = new MockMultipartFile("audio", "new_sound.mp3", "audio/mpeg", "sound file".getBytes());

        mockMvc.perform(multipart("/admin/sounds/audio/{soundId}", 1)
                        .file(audio)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-sound-post-update-audio",
                        pathParameters(
                                parameterWithName("soundId").description("수정할 사운드의 ID입니다.")
                        ),
                        requestParts(
                                partWithName("audio").description("수정할 사운드의 오디오 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : '[#id]번 사운드의 오디오가 수정되었습니다.'"),
                                fieldWithPath("deletedAudioId").description("삭제된 오디오 파일 아이디입니다."),
                                fieldWithPath("newAudioId").description("새로운 오디오 파일 아이디입니다.")
                        )
                ));

        List<FileJpaEntity> filesInDB = fileJpaRepository.findAll();
        assertThat(filesInDB).hasSize(11);
        assertThat(filesInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_sound.mp3"));
        assertThat(soundJpaRepository.findById(1L).orElseThrow().getAudioFileId()).isEqualTo(filesInDB.getLast().getId());
    }

}