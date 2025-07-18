package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.dto.result.CreateUniverseResult;
import com.hoo.universe.api.in.CreateUniverseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateUniverseControllerTest extends DocumentationTest {

    @Autowired
    private CreateUniverseUseCase createUniverseUseCase;

    @Test
    @DisplayName("유니버스 생성 API")
    void testCreateUniverse() throws Exception {

        //language=JSON
        String metadata = """
                {
                  "title": "우주",
                  "description": "유니버스는 우주입니다.",
                  "ownerID": "019812d4-73e3-7d80-9e8e-aa62af94c0b3",
                  "categoryID": "019812d4-73e3-7b78-a49e-b8a68b70cfb6",
                  "accessLevel": "PUBLIC",
                  "hashtags": [
                    "우주", "행성", "지구", "별"
                  ]
                }
                """;

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile background = new MockMultipartFile("background", "universe_inner_image.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbmusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());

        when(createUniverseUseCase.createNewUniverse(argThat( command ->
                        command.metadata().title().equals("우주") &&
                        command.metadata().description().equals("유니버스는 우주입니다.") &&
                        command.metadata().ownerID().toString().equals("019812d4-73e3-7d80-9e8e-aa62af94c0b3") &&
                        command.metadata().categoryID().toString().equals("019812d4-73e3-7b78-a49e-b8a68b70cfb6") &&
                        command.metadata().accessLevel().equals("PUBLIC") &&
                        command.metadata().hashtags().containsAll(List.of( "우주", "행성", "지구", "별")
                        ))))
                .thenReturn(new CreateUniverseResult(
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch(),
                        ZonedDateTime.now().toEpochSecond(),
                        UuidCreator.getTimeOrderedEpoch(),
                        "유니버스",
                        "유니버스는 우주입니다.",
                        "leaf",
                        "PUBLIC",
                        List.of("우주", "행성", "지구", "별")
                ));

        mockMvc.perform(multipart("/universes")
                        .file(thumbnail).file(thumbMusic).file(background)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(201))
                .andDo(document("create-universe",
                        requestParts(
                                partWithName("metadata").description("생성할 유니버스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("thumbnail").description("생성할 유니버스의 썸네일 이미지입니다."),
                                partWithName("thumbmusic").description("생성할 유니버스의 썸뮤직 오디오입니다."),
                                partWithName("background").description("생성할 유니버스의 내부이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("universeID").description("생성된 유니버스의 아이디입니다."),
                                fieldWithPath("thumbnailID").description("생성된 유니버스의 썸네일 파일 ID입니다."),
                                fieldWithPath("thumbMusicID").description("생성된 유니버스의 썸뮤직 파일 ID입니다."),
                                fieldWithPath("backgroundID").description("생성된 유니버스의 내부 이미지 파일 ID입니다."),
                                fieldWithPath("ownerID").description("생성된 유니버스 작성자의 ID입니다."),
                                fieldWithPath("createdTime").description("유니버스의 생성(등록)일자입니다."),
                                fieldWithPath("title").description("생성된 유니버스의 제목입니다."),
                                fieldWithPath("owner").description("생성된 유니버스의 작성자의 닉네임입니다."),
                                fieldWithPath("description").description("생성된 유니버스의 설명입니다."),
                                fieldWithPath("categoryID").description("생성된 유니버스의 카테고리 ID입니다."),
                                fieldWithPath("accessLevel").description("생성된 유니버스의 공개 여부입니다."),
                                fieldWithPath("hashtags").description("생성된 유니버스의 해시태그 리스트입니다.")
                        )
                ));
    }

}