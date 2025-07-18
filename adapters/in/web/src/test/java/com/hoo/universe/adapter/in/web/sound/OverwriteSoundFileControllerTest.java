package com.hoo.universe.adapter.in.web.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.sound.OverwriteSoundFileResult;
import com.hoo.universe.api.in.sound.OverwriteSoundFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OverwriteSoundFileControllerTest extends DocumentationTest {

    @Autowired
    private OverwriteSoundFileUseCase overwriteSoundFileUseCase;

    @Test
    @DisplayName("사운드 파일 덮어쓰기")
    void overwriteSoundFile() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID parentPieceID = UuidCreator.getTimeOrderedEpoch();
        UUID soundID = UuidCreator.getTimeOrderedEpoch();
        MockMultipartFile audio = new MockMultipartFile("audio", "new_sound.mp3", "audio/mpeg", "sound file".getBytes());

        when(overwriteSoundFileUseCase.overwriteSoundAudio(any(), any(), any(), any()))
                .thenReturn(new OverwriteSoundFileResult(
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch()
                ));

        mockMvc.perform(multipart("/universes/{universeID}/pieces/{parentPieceID}/sound/{soundID}/audio",
                        universeID, parentPieceID, soundID)
                        .file(audio)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200))
                .andDo(document("overwrite-sound-audio",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 사운드를 소유한 유니버스 ID입니다."),
                                parameterWithName("parentPieceID").description("수정할 사운드를 소유한 피스 ID입니다."),
                                parameterWithName("soundID").description("수정할 사운드의 ID입니다.")
                        ),
                        requestParts(
                                partWithName("audio").description("수정할 사운드의 오디오 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedAudioID").description("삭제된 오디오 파일 아이디입니다."),
                                fieldWithPath("newAudioID").description("새로운 오디오 파일 아이디입니다.")
                        )
                ));

    }
}