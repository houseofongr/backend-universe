package com.hoo.admin.adapter.in.web.sound;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class PostSoundControllerTest extends AbstractControllerTest {

    //language=JSON
    String metadata = """
            {
              "pieceId": 1,
              "title": "소리",
              "description": "사운드는 소리입니다.",
              "hidden": false
            }
            """;

    @Test
    @DisplayName("사운드 생성 API")
    void testCreateSound() throws Exception {

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile sound = new MockMultipartFile("audio", "audio.mp3", "audio/mpeg", "audio file".getBytes());

        mockMvc.perform(multipart("/admin/sounds")
                        .part(metadataPart).file(sound)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-sound-post",
                        requestParts(
                                partWithName("metadata").description("생성할 사운드의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("audio").description("생성할 사운드의 음원 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : '[#id]번 사운드가 생성되었습니다.'"),
                                fieldWithPath("soundId").description("생성된 사운드의 ID입니다."),
                                fieldWithPath("pieceId").description("생성된 사운드를 포함하는 피스의 ID입니다."),
                                fieldWithPath("audioFileId").description("생성된 사운드의 내부 음원파일 ID입니다."),
                                fieldWithPath("title").description("생성된 사운드의 제목입니다."),
                                fieldWithPath("description").description("생성된 사운드의 상세정보입니다."),
                                fieldWithPath("hidden").description("생성된 사운드의 숨김 여부입니다.")
                        )
                ));
    }
}