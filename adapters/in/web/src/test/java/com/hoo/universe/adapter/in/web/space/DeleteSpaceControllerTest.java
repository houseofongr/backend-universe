package com.hoo.universe.adapter.in.web.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.piece.DeletePieceResult;
import com.hoo.universe.api.dto.result.space.DeleteSpaceResult;
import com.hoo.universe.api.in.space.DeleteSpaceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteSpaceControllerTest extends DocumentationTest {

    @Autowired
    private DeleteSpaceUseCase deleteSpaceUseCase;

    @Test
    @DisplayName("스페이스 삭제")
    void deleteSpace() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID spaceID = UuidCreator.getTimeOrderedEpoch();

        when(deleteSpaceUseCase.deleteSpace(universeID, spaceID))
                .thenReturn(new DeleteSpaceResult(
                        List.of(UuidCreator.getTimeOrderedEpoch()),
                        List.of(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch()),
                        List.of(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch()),
                        List.of(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch())
                ));

        mockMvc.perform(delete("/universes/{universeID}/spaces/{spaceID}", universeID, spaceID))
                .andExpect(status().is(200))
                .andDo(document("delete-space",
                        pathParameters(
                                parameterWithName("universeID").description("삭제할 스페이스를 소유한 유니버스 ID입니다."),
                                parameterWithName("spaceID").description("삭제할 스페이스 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedSpaceIDs").description("삭제된 스페이스 ID 리스트입니다."),
                                fieldWithPath("deletedPieceIDs").description("삭제된 피스 ID 리스트입니다."),
                                fieldWithPath("deletedSoundIDs").description("삭제된 사운드 ID 리스트입니다."),
                                fieldWithPath("deletedFileIDs").description("삭제된 파일 ID 리스트입니다.")
                        )
                ));
    }

}