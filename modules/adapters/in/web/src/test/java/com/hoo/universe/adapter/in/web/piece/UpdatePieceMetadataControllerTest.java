package com.hoo.universe.adapter.in.web.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.piece.UpdatePieceMetadataResult;
import com.hoo.universe.api.in.piece.UpdatePieceMetadataUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdatePieceMetadataControllerTest extends DocumentationTest {

    @Autowired
    private UpdatePieceMetadataUseCase updatePieceMetadataUseCase;

    @Test
    @DisplayName("피스 상세정보 수정")
    void updatePieceMetadata() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID pieceID = UuidCreator.getTimeOrderedEpoch();
        //language=JSON
        String content = """
                {
                  "title": "평화",
                  "description": "피스는 평화입니다.",
                  "hidden": false
                }
                """;

        when(updatePieceMetadataUseCase.updatePieceMetadata(any(), any(), argThat(command ->
                command.title().equals("평화") &&
                command.description().equals("피스는 평화입니다.") &&
                command.hidden().equals(false))))
                .thenReturn(new UpdatePieceMetadataResult(
                    "평화",
                        "피스는 평화입니다.",
                        false,
                        ZonedDateTime.now().toEpochSecond()
                ));

        mockMvc.perform(patch("/universes/{universeID}/pieces/{pieceID}", universeID, pieceID)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("update-piece-metadata",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 피스를 소유한 ID입니다."),
                                parameterWithName("pieceID").description("수정할 피스의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다.").optional(),
                                fieldWithPath("description").description("수정할 상세정보입니다.").optional(),
                                fieldWithPath("hidden").description("수정할 숨김 여부입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("title").description("수정된 제목입니다."),
                                fieldWithPath("description").description("수정된 상세정보입니다."),
                                fieldWithPath("hidden").description("수정된 숨김 여부입니다."),
                                fieldWithPath("updatedTime").description("수정된 시간입니다.")
                        )
                ));
    }
}