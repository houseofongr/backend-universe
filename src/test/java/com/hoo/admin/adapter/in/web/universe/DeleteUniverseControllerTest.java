package com.hoo.admin.adapter.in.web.universe;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:/sql/universe.sql")
class DeleteUniverseControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @Transactional
    @DisplayName("유니버스 삭제 API")
    void testDeleteUniverse() throws Exception {

        // Universe Thumb music(= 1)
        saveFile(1L, FileType.AUDIO);

        // [Universe Thumbnail + innerImage](= 2)
        saveFile(2L, FileType.IMAGE);
        saveFile(3L, FileType.IMAGE);

        // Space innerImage(37 ~ 41)
        for (long i = 37; i <= 41; i++) saveFile(i, FileType.IMAGE);

        // Sound audio(42 ~ 52)
        for (long i = 42; i <= 52; i++) saveFile(i, FileType.AUDIO);

        mockMvc.perform(delete("/admin/universes/{universeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-delete",
                        pathParameters(
                                parameterWithName("universeId").description("삭제할 유니버스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : '[#id]번 유니버스가 삭제되었습니다.'"),
                                fieldWithPath("deletedUniverseId").description("삭제된 유니버스 ID입니다."),
                                fieldWithPath("deletedSpaceIds").description("삭제된 스페이스 ID 리스트입니다."),
                                fieldWithPath("deletedPieceIds").description("삭제된 피스 ID 리스트입니다."),
                                fieldWithPath("deletedSoundIds").description("삭제된 사운드 ID 리스트입니다."),
                                fieldWithPath("deletedImageFileIds").description("삭제된 이미지 파일 ID 리스트입니다."),
                                fieldWithPath("deletedAudioFileIds").description("삭제된 오디오 파일 ID 리스트입니다.")
                        )
                ));


        // 파일 체크
        assertThat(fileJpaRepository.findAll()).isEmpty();

        // 스페이스 체크
        List<SpaceJpaEntity> spaces = em.createQuery("select s from SpaceJpaEntity s where s.universeId = 1", SpaceJpaEntity.class).getResultList();
        assertThat(spaces).isEmpty();

        // 피스 체크
        List<PieceJpaEntity> pieces = em.createQuery("select s from PieceJpaEntity s where s.universeId = 1", PieceJpaEntity.class).getResultList();
        assertThat(pieces).isEmpty();

        // 사운드 체크
        List<SoundJpaEntity> sounds = em.createQuery("select s from SoundJpaEntity s", SoundJpaEntity.class).getResultList();
        assertThat(sounds).isEmpty();
    }
}