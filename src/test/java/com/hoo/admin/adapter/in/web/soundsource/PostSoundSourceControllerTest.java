package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.admin.application.port.in.soundsource.CreateSoundSourceResult;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;

import static com.hoo.common.util.GsonUtil.gson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostSoundSourceControllerTest extends AbstractControllerTest {

    @Autowired
    SoundSourceJpaRepository soundSourceJpaRepository;


    @Test
    @Sql("PostSoundSourceControllerTest.sql")
    @DisplayName("음원 생성 API 테스트")
    void testCreateSoundSourceAPI() throws Exception {

        //language=JSON
        String metadata = """
                {
                  "name" : "골골송",
                  "description" : "2025년 설이가 보내는 골골송",
                  "isActive" : true
                }
                """;

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile soundFile = new MockMultipartFile("soundFile", "golgolSong.mp3", "audio/mpeg", "golgolgolgolgolgolgolgol".getBytes());

        String response = mockMvc.perform(multipart("/admin/items/{itemId}/sound-sources", 1L)
                        .part(metadataPart)
                        .file(soundFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(201))
                .andDo(document("admin-soundsource-post",
                        pathParameters(
                                parameterWithName("itemId").description("해당 음원을 소유할 아이템 ID입니다.")
                        ),
                        requestParts(
                                partWithName("metadata").description("생성할 음원 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("soundFile").description("저장할 음악 파일의 Form 태그 name 속성값입니다.(이름 변경 X) +" + "\n" + "* 지원 파일형식 : [mp3, wav]")
                        ),
                        responseFields(
                                fieldWithPath("soundSourceId").description("생성된 음원 파일 아이디입니다.")
                        )
                ))
                .andReturn().getResponse().getContentAsString();

        CreateSoundSourceResult result = gson.fromJson(response, CreateSoundSourceResult.class);
        assertThat(soundSourceJpaRepository.findById(result.soundSourceId())).isNotEmpty();
        assertThat(soundSourceJpaRepository.findById(result.soundSourceId()).get().getName()).isEqualTo("골골송");
        assertThat(soundSourceJpaRepository.findById(result.soundSourceId()).get().getDescription()).isEqualTo("2025년 설이가 보내는 골골송");
        assertThat(soundSourceJpaRepository.findById(result.soundSourceId()).get().getIsActive()).isTrue();
    }
}