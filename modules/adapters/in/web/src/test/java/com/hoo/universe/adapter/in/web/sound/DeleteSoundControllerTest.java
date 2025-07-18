package com.hoo.universe.adapter.in.web.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.sound.DeleteSoundResult;
import com.hoo.universe.api.in.sound.DeleteSoundUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteSoundControllerTest extends DocumentationTest {

    @Autowired
    private DeleteSoundUseCase deleteSoundUseCase;

    @Test
    @DisplayName("사운드 삭제")
    void deleteSound() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID spaceID = UuidCreator.getTimeOrderedEpoch();
        UUID soundID = UuidCreator.getTimeOrderedEpoch();

        when(deleteSoundUseCase.deleteSound(universeID, spaceID, soundID))
                .thenReturn(new DeleteSoundResult(UuidCreator.getTimeOrderedEpoch()));

        mockMvc.perform(delete("/universes/{universeID}/pieces/{parentPieceID}/sounds/{soundID}", universeID, spaceID, soundID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("delete-sound",
                        pathParameters(
                                parameterWithName("universeID").description("삭제할 사운드를 소유한 유니버스 ID입니다."),
                                parameterWithName("parentPieceID").description("삭제할 사운드를 소유한 피스 ID입니다."),
                                parameterWithName("soundID").description("삭제할 사운드 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedSoundID").description("삭제된 사운드 ID입니다.")
                        )
                ));
    }

}