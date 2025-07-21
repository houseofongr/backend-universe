package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.dto.OverwriteUniverseFileResult;
import com.hoo.universe.api.in.OverwriteUniverseFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OverwriteUniverseFileControllerTest extends DocumentationTest {

    @Autowired
    private OverwriteUniverseFileUseCase overwriteUniverseFileUseCase;

    @Test
    @DisplayName("유니버스 썸뮤직 덮어쓰기")
    void overwriteUniverseThumbmusic() throws Exception {

        MockMultipartFile thumbmusic = new MockMultipartFile("thumbmusic", "new_universe_thumb.mp3", "audio/mpeg", "universe file".getBytes());

        when(overwriteUniverseFileUseCase.overwriteUniverseThumbmusic(any(), argThat(command ->
                command.name().equals(thumbmusic.getName()) &&
                command.size().equals(thumbmusic.getSize()))))
                .thenReturn(new OverwriteUniverseFileResult.Thumbmusic(
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch()
                ));

        mockMvc.perform(multipart("/universes/{universeID}/thumbmusic", UuidCreator.getTimeOrderedEpoch())
                        .file(thumbmusic)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200))
                .andDo(document("overwrite-universe-thumbmusic",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 유니버스의 ID입니다.")
                        ),
                        requestParts(
                                partWithName("thumbmusic").description("수정할 유니버스의 썸뮤직 오디오 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedThumbmusicID").description("삭제된 썸뮤직 아이디입니다."),
                                fieldWithPath("newThumbmusicID").description("새로운 썸뮤직 아이디입니다.")
                        )
                ));
    }

    @Test
    @DisplayName("유니버스 썸네일 덮어쓰기")
    void overwriteUniverseThumbnail() throws Exception {

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "new_universe_thumb.png", "image/png", "universe file".getBytes());

        when(overwriteUniverseFileUseCase.overwriteUniverseThumbnail(any(), argThat(command ->
                command.name().equals(thumbnail.getName()) &&
                command.size().equals(thumbnail.getSize()))))
                .thenReturn(new OverwriteUniverseFileResult.Thumbnail(
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch()
                ));

        mockMvc.perform(multipart("/universes/{universeID}/thumbnail", UuidCreator.getTimeOrderedEpoch())
                        .file(thumbnail)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("overwrite-universe-thumbnail",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 유니버스의 ID입니다.")
                        ),
                        requestParts(
                                partWithName("thumbnail").description("수정할 유니버스의 썸네일 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedThumbnailID").description("삭제된 썸네일 아이디입니다."),
                                fieldWithPath("newThumbnailID").description("새로운 썸네일 아이디입니다.")
                        )
                ));
    }

    @Test
    @DisplayName("유니버스 백그라운드 덮어쓰기")
    void overwriteUniverseBackground() throws Exception {

        MockMultipartFile background = new MockMultipartFile("background", "new_universe_background.png", "image/png", "universe file".getBytes());

        when(overwriteUniverseFileUseCase.overwriteUniverseBackground(any(), argThat(command ->
                command.name().equals(background.getName()) &&
                command.size().equals(background.getSize()))))
                .thenReturn(new OverwriteUniverseFileResult.Background(
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch()
                ));

        mockMvc.perform(multipart("/universes/{universeID}/background", UuidCreator.getTimeOrderedEpoch())
                        .file(background)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("overwrite-universe-background",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 유니버스의 ID입니다.")
                        ),
                        requestParts(
                                partWithName("background").description("수정할 유니버스의 배경 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("deletedBackgroundID").description("삭제된 배경 아이디입니다."),
                                fieldWithPath("newBackgroundID").description("새로운 배경 아이디입니다.")
                        )
                ));
    }
}