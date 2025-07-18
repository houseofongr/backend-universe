package com.hoo.admin.adapter.in.web.piece;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class DeletePieceControllerTest extends AbstractControllerTest {

    @Autowired
    PieceJpaRepository pieceJpaRepository;

    @Test
    @DisplayName("피스 삭제 API")
    void testDeletePiece() throws Exception {

        mockMvc.perform(delete("/admin/pieces/{pieceId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-piece-delete",
                        pathParameters(
                                parameterWithName("pieceId").description("삭제할 피스 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : '[#id]번 피스가 삭제되었습니다.'"),
                                fieldWithPath("deletedPieceId").description("삭제된 피스 ID입니다."),
                                fieldWithPath("deletedSoundIds").description("삭제된 사운드 ID 리스트입니다."),
                                fieldWithPath("deletedImageFileIds").description("삭제된 이미지 파일 ID 리스트입니다."),
                                fieldWithPath("deletedAudioFileIds").description("삭제된 오디오 파일 ID 리스트입니다.")
                        )
                ));

        assertThat(pieceJpaRepository.findAll()).noneMatch(pieceJpaEntity -> pieceJpaEntity.getId().equals(1L));
    }
}