package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.web.dto.result.DeleteUniverseResult;
import com.hoo.universe.api.in.web.usecase.DeleteUniverseUseCase;
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

class DeleteUniverseControllerTest extends DocumentationTest {

    @Autowired
    private DeleteUniverseUseCase deleteUniverseUseCase;

    @Test
    @DisplayName("유니버스 삭제 API")
    void testDeleteUniverse() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        when(deleteUniverseUseCase.deleteUniverse(universeID))
                .thenReturn(new DeleteUniverseResult(
                        universeID,
                        List.of(UuidCreator.getTimeOrderedEpoch()),
                        List.of(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch()),
                        List.of(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch()),
                        10
                ));

        mockMvc.perform(delete("/universes/{universeID}", universeID))
                .andExpect(status().is(200))
                .andDo(document("delete-universe",
                        pathParameters(
                                parameterWithName("universeID").description("삭제할 유니버스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedUniverseID").description("삭제된 유니버스 ID입니다."),
                                fieldWithPath("deletedSpaceIDs").description("삭제된 스페이스 ID 리스트입니다."),
                                fieldWithPath("deletedPieceIDs").description("삭제된 피스 ID 리스트입니다."),
                                fieldWithPath("deletedSoundIDs").description("삭제된 사운드 ID 리스트입니다."),
                                fieldWithPath("deletedFileCount").description("삭제된 파일 개수입니다.")
                        )
                ));
    }

}