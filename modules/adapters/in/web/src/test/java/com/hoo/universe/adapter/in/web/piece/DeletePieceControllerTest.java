package com.hoo.universe.adapter.in.web.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.in.web.dto.result.DeletePieceResult;
import com.hoo.universe.api.in.web.usecase.DeletePieceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeletePieceControllerTest extends DocumentationTest {

    @Autowired
    private DeletePieceUseCase deletePieceUseCase;

    @Test
    @DisplayName("피스 삭제")
    void deletePiece() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID pieceID = UuidCreator.getTimeOrderedEpoch();

        when(deletePieceUseCase.deletePiece(universeID, pieceID))
                .thenReturn(new DeletePieceResult(
                        UuidCreator.getTimeOrderedEpoch(),
                        List.of(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch()),
                        3
                ));

        mockMvc.perform(delete("/universes/{universeID}/pieces/{pieceID}", universeID, pieceID))
                .andExpect(status().is(200))
                .andDo(document("delete-piece",
                        pathParameters(
                                parameterWithName("universeID").description("삭제할 피스를 소유한 유니버스 ID입니다."),
                                parameterWithName("pieceID").description("삭제할 피스 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedPieceID").description("삭제된 피스 ID입니다."),
                                fieldWithPath("deletedSoundIDs").description("삭제된 사운드 ID 리스트입니다."),
                                fieldWithPath("deletedFileCount").description("삭제된 파일 개수입니다.")
                        )
                ));
    }

}