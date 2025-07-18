package com.hoo.universe.adapter.in.web.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.sound.UpdateSoundMetadataResult;
import com.hoo.universe.api.in.sound.UpdateSoundMetadataUseCase;
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

class UpdateSoundMetadataControllerTest extends DocumentationTest {

    @Autowired
    private UpdateSoundMetadataUseCase updateSoundMetadataUseCase;

    @Test
    @DisplayName("사운드 상세정보 수정")
    void updateSoundMetadata() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID parentPieceID = UuidCreator.getTimeOrderedEpoch();
        UUID soundID = UuidCreator.getTimeOrderedEpoch();
        //language=JSON
        String content = """
                {
                  "title" : "수정할 제목을 입력합니다.",
                  "description" : "수정할 내용을 입력합니다.",
                  "hidden" : "false"
                }
                """;

        when(updateSoundMetadataUseCase.updateSoundMetadata(any(), any(), any(), argThat(command ->
                command.title().equals("수정할 제목을 입력합니다.") &&
                command.description().equals("수정할 내용을 입력합니다.") &&
                command.hidden().equals(false))))
                .thenReturn(new UpdateSoundMetadataResult(
                        "수정할 제목을 입력합니다.",
                        "수정할 내용을 입력합니다.",
                        false,
                        ZonedDateTime.now().toEpochSecond()
                ));

        mockMvc.perform(patch("/universes/{universeID}/pieces/{parentPieceID}/sound/{soundID}", universeID, parentPieceID, soundID)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("update-sound-metadata",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 사운드를 소유한 유니버스 ID입니다."),
                                parameterWithName("parentPieceID").description("수정할 사운드를 소유한 피스 ID입니다."),
                                parameterWithName("soundID").description("수정할 사운드의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다.").optional(),
                                fieldWithPath("description").description("수정할 상세 설명입니다.").optional(),
                                fieldWithPath("hidden").description("수정할 숨김 여부입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("title").description("수정된 제목입니다."),
                                fieldWithPath("description").description("수정된 상세 설명입니다."),
                                fieldWithPath("hidden").description("수정된 숨김 여부입니다."),
                                fieldWithPath("updatedTime").description("수정된 시간입니다.")
                        )
                ));
    }
}