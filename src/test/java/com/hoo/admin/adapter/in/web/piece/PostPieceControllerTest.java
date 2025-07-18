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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class PostPieceControllerTest extends AbstractControllerTest {

    //language=JSON
    String content = """
            {
              "universeId": 1,
              "parentSpaceId": 1,
              "title": "조각",
              "description": "피스는 조각입니다.",
              "startX": 0.1,
              "startY": 0.2,
              "endX": 0.3,
              "endY": 0.4,
              "hidden": false
            }
            """;

    @Autowired
    PieceJpaRepository pieceJpaRepository;

    @Test
    @DisplayName("피스 생성 API")
    void testCreatePiece() throws Exception {

        mockMvc.perform(post("/admin/pieces/position")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-piece-post",
                        requestFields(
                                fieldWithPath("universeId").description("생성할 피스를 포함하는 유니버스의 ID입니다."),
                                fieldWithPath("parentSpaceId").description("생성할 피스를 포함하는 상위 스페이스의 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("title").description("생성할 피스의 제목입니다."),
                                fieldWithPath("description").description("생성할 피스의 상세정보입니다."),
                                fieldWithPath("startX").description("생성할 피스의 시작좌표(x)입니다."),
                                fieldWithPath("startY").description("생성할 피스의 시작좌표(y)입니다."),
                                fieldWithPath("endX").description("생성할 피스의 종료좌표(x)입니다."),
                                fieldWithPath("endY").description("생성할 피스의 종료좌표(y)입니다."),
                                fieldWithPath("hidden").description("생성할 피스의 숨김여부입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : '[#id]번 피스가 생성되었습니다.'"),
                                fieldWithPath("pieceId").description("생성된 피스의 ID입니다."),
                                fieldWithPath("title").description("생성된 피스의 제목입니다."),
                                fieldWithPath("description").description("생성된 피스의 상세정보입니다."),
                                fieldWithPath("startX").description("생성된 피스의 시작좌표(x)입니다."),
                                fieldWithPath("startY").description("생성된 피스의 시작좌표(y)입니다."),
                                fieldWithPath("endX").description("생성된 피스의 종료좌표(x)입니다."),
                                fieldWithPath("endY").description("생성된 피스의 종료좌표(y)입니다."),
                                fieldWithPath("hidden").description("생성된 피스의 숨김여부입니다.")
                        )
                ));

        assertThat(pieceJpaRepository.findAll().getLast().getInnerImageFileId()).isNull();
    }
}