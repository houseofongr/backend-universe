package com.hoo.universe.adapter.in.web.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.piece.MovePieceWithTwoPointResult;
import com.hoo.universe.api.in.piece.MovePieceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MovePieceControllerTest extends DocumentationTest {


    @Autowired
    private MovePieceUseCase movePieceUseCase;

    @Test
    @DisplayName("피스 이동")
    void movePiece() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID pieceID = UuidCreator.getTimeOrderedEpoch();
        //language=JSON
        String content = """
                {
                  "startX": 0.6,
                  "startY": 0.7,
                  "endX": 0.5,
                  "endY": 0.4
                }
                """;

        when(movePieceUseCase.movePieceWithTwoPoint(any(), any(), argThat(command ->
                command.startX().equals(0.6) &&
                command.startY().equals(0.7) &&
                command.endX().equals(0.5) &&
                command.endY().equals(0.4))))
                .thenReturn(new MovePieceWithTwoPointResult(
                    0.6, 0.7, 0.5, 0.4
                ));

        mockMvc.perform(patch("/universes/{universeID}/pieces/{pieceID}/move", universeID, pieceID)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("move-piece",
                        pathParameters(
                                parameterWithName("universeID").description("이동할 피스를 소유한 유니버스의 ID입니다."),
                                parameterWithName("pieceID").description("이동할 피스의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("startX").description("수정할 X축 시작좌표입니다."),
                                fieldWithPath("startY").description("수정할 Y축 시작좌표입니다."),
                                fieldWithPath("endX").description("수정할 X축 종료좌표입니다."),
                                fieldWithPath("endY").description("수정할 Y축 종료좌표입니다.")
                        ),
                        responseFields(
                                fieldWithPath("startX").description("수정된 X축 시작좌표입니다."),
                                fieldWithPath("startY").description("수정된 Y축 시작좌표입니다."),
                                fieldWithPath("endX").description("수정된 X축 종료좌표입니다."),
                                fieldWithPath("endY").description("수정된 Y축 종료좌표입니다.")
                        )
                ));
    }

}