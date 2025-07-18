package com.hoo.universe.adapter.in.web.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.sound.CreateSoundResult;
import com.hoo.universe.api.in.sound.CreateSoundUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateSoundControllerTest extends DocumentationTest {

    @Autowired
    private CreateSoundUseCase createSoundUseCase;

    @Test
    @DisplayName("사운드 생성")
    void createSound() throws Exception {


        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID parentPieceID = UuidCreator.getTimeOrderedEpoch();
        //language=JSON
        String metadata = """
                {
                  "title": "소리",
                  "description": "사운드는 소리입니다.",
                  "hidden": false
                }
                """;

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        when(createSoundUseCase.createNewSound(any(), any(), argThat(command ->
                command.metadata().title().equals("소리") &&
                command.metadata().description().equals("사운드는 소리입니다.") &&
                command.metadata().hidden().equals(false)
        )))
                .thenReturn(new CreateSoundResult(
                        UuidCreator.getTimeOrderedEpoch(),
                        URI.create("http://example.com/files/audio.mp3"),
                        "소리",
                        "사운드는 소리입니다.",
                        false,
                        ZonedDateTime.now().toEpochSecond()
                ));

        MockMultipartFile sound = new MockMultipartFile("audio", "audio.mp3", "audio/mpeg", "audio file".getBytes());

        mockMvc.perform(multipart("/universes/{universeID}/pieces/{parentPieceID}/sounds", universeID, parentPieceID)
                        .part(metadataPart)
                        .file(sound)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(201))
                .andDo(document("create-sound",
                        pathParameters(
                                parameterWithName("universeID").description("생성할 사운드를 소유한 유니버스 ID입니다."),
                                parameterWithName("parentPieceID").description("생성할 사운드를 소유한 피스 ID입니다.")
                        ),
                        requestParts(
                                partWithName("metadata").description("생성할 사운드의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("audio").description("생성할 사운드의 음원 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("soundID").description("생성된 사운드의 ID입니다."),
                                fieldWithPath("audioFileUrl").description("생성된 사운드의 내부 음원파일 ID입니다."),
                                fieldWithPath("title").description("생성된 사운드의 제목입니다."),
                                fieldWithPath("description").description("생성된 사운드의 상세정보입니다."),
                                fieldWithPath("hidden").description("생성된 사운드의 숨김 여부입니다."),
                                fieldWithPath("createdTime").description("생성 시간입니다.")
                        )
                ));
    }

}