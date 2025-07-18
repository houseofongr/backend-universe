package com.hoo.universe.adapter.in.web.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.piece.CreatePieceResult;
import com.hoo.universe.api.dto.result.sound.CreateSoundResult;
import com.hoo.universe.api.in.piece.CreatePieceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.request.RequestDocumentation;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreatePieceControllerTest extends DocumentationTest {

    @Autowired
    private CreatePieceUseCase createPieceUseCase;

    @Test
    @DisplayName("피스 생성")
    void createPiece() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID parentSpaceID = UuidCreator.getTimeOrderedEpoch();
        UUID pieceID = UuidCreator.getTimeOrderedEpoch();
        //language=JSON
        String metadata = String.format("""
                {
                  "parentSpaceID": "%s",
                  "title": "조각",
                  "description": "피스는 조각입니다.",
                  "startX": 0.1,
                  "startY": 0.2,
                  "endX": 0.3,
                  "endY": 0.4,
                  "hidden": false
                }
                """, parentSpaceID);
        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        when(createPieceUseCase.createNewPieceWithTwoPoint(any(), argThat(command ->
                command.metadata().title().equals("조각") &&
                command.metadata().description().equals("피스는 조각입니다.") &&
                command.metadata().startX().equals(0.1) &&
                command.metadata().startY().equals(0.2) &&
                command.metadata().endX().equals(0.3) &&
                command.metadata().endY().equals(0.4) &&
                command.metadata().hidden().equals(false)
        )))
                .thenReturn(new CreatePieceResult(
                        UuidCreator.getTimeOrderedEpoch(),
                        "조각",
                        "피스는 조각입니다.",
                        0.1,
                        0.2,
                        0.3,
                        0.4,
                        false,
                        ZonedDateTime.now().toEpochSecond()
                ));

        mockMvc.perform(multipart("/universes/{universeID}/pieces",
                        universeID, pieceID)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(201))
                .andDo(document("create-piece",
                        pathParameters(
                                parameterWithName("universeID").description("생성할 피스를 소유한 유니버스 ID입니다.")
                        ),
                        requestParts(
                                partWithName("metadata").description("생성할 피스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("image").description("생성할 피스의 이미지 파일입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("pieceID").description("생성된 피스의 ID입니다."),
                                fieldWithPath("title").description("생성된 피스의 제목입니다."),
                                fieldWithPath("description").description("생성된 피스의 상세정보입니다."),
                                fieldWithPath("startX").description("생성된 피스의 시작좌표(x)입니다."),
                                fieldWithPath("startY").description("생성된 피스의 시작좌표(y)입니다."),
                                fieldWithPath("endX").description("생성된 피스의 종료좌표(x)입니다."),
                                fieldWithPath("endY").description("생성된 피스의 종료좌표(y)입니다."),
                                fieldWithPath("hidden").description("생성된 피스의 숨김여부입니다."),
                                fieldWithPath("createdTime").description("생성된 피스의 생성시간입니다.")
                        )
                ));
    }

}